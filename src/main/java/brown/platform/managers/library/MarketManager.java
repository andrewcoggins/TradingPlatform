package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.auction.marketstate.library.MarketState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.ErrorMessage;
import brown.communication.messages.library.TradeRejectionMessage;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.managers.IMarketManager;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;
import brown.platform.market.IMarketBlock;
import brown.platform.market.library.Market;
import brown.platform.market.library.SimultaneousMarket;
import brown.platform.tradeable.ITradeable;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.whiteboard.library.Whiteboard;

/**
 * Market manager stores and handles multiple markets
 * 
 * @author acoggins
 *
 */
public class MarketManager implements IMarketManager {
  private Map<Integer, IMarket> activeMarkets;
  private List<IMarketBlock> allMarkets;
  private IWhiteboard whiteboard;
  private Integer marketIndex;
  private boolean lock;

  /**
   * Constructor for a market manager initializes ledgers and markets. ledgers
   * tracks the ledgers for all markets at each time markets maps each market at
   * each time to a unique id information is initially a blank prevstateinfo
   * index is initially set to -1 idCount keeps track of the number of markets
   * in the MarketManager- initially, this is 0.
   */
  public MarketManager() {
    this.allMarkets = new LinkedList<IMarketBlock>();
    this.activeMarkets = new ConcurrentHashMap<Integer, IMarket>();
    this.lock = false;
    this.whiteboard = new Whiteboard();
    this.marketIndex = 0;
  }

  @Override
  public void createSimultaneousMarket(List<IFlexibleRules> marketRules,
      List<List<String>> marketTradeableNames,
      Map<String, List<ITradeable>> allTradeables) {
    if (!this.lock) {
      List<Map<String, List<ITradeable>>> marketTradeables =
          new LinkedList<Map<String, List<ITradeable>>>();
      for (List<String> tList : marketTradeableNames) {
        Map<String, List<ITradeable>> singleMarketTradeables =
            new HashMap<String, List<ITradeable>>();
        for (String tName : tList) {
          singleMarketTradeables.put(tName, allTradeables.get(tName));
        }
        marketTradeables.add(singleMarketTradeables);
      }
      IMarketBlock marketBlock =
          new SimultaneousMarket(marketRules, marketTradeables);
      this.allMarkets.add(marketBlock);
    } else {
      PlatformLogging.log("ERROR: market manager locked.");
    }
  }

  @Override
  public void lock() {
    this.lock = true;
  }

  @Override
  public Integer getNumMarketBlocks() {
    return this.allMarkets.size();
  }

  @Override
  public void openMarkets(int index) {
    // TODO: somehow open markets using whiteboard information.
    IMarketBlock currentMarketBlock = this.allMarkets.get(index);
    List<IFlexibleRules> marketRules = currentMarketBlock.getMarkets();
    List<Map<String, List<ITradeable>>> marketTradeables =
        currentMarketBlock.getMarketTradeables();
    for (int i = 0; i < marketRules.size(); i++) {
      this.activeMarkets.put(this.marketIndex,
          new Market(this.marketIndex, marketRules.get(i), new MarketState(),
              new MarketPublicState(), marketTradeables.get(i)));
    }
  }

  @Override
  public IStatusMessage handleTradeMessage(ITradeMessage message) {
    Integer marketID = message.getAuctionID();
    Integer agentID = message.getAgentID(); 
    if (this.activeMarkets.containsKey(marketID)) {
      IMarket market = this.activeMarkets.get(marketID);
      synchronized (market) {
        boolean accepted = market.processBid(message);
        if (!accepted) {
          return new TradeRejectionMessage(0, agentID, 
              "[x] REJECTED: Trade message for auction "
                  + message.getAuctionID().toString()
                  + " denied: rejected by activity rule.");
        } else {
          return new TradeRejectionMessage(-1, -1, "");
        }
      }
    } else {
      return new ErrorMessage(0, agentID,
          "[x] ERROR: Trade message for auction "
              + message.getAuctionID().toString()
              + " denied: market no longer active.");
    }
  }

  @Override
  public List<Integer> getActiveMarketIDs() {
    return new LinkedList<Integer>(this.activeMarkets.keySet());
  }

  @Override
  public IMarket getActiveMarket(Integer marketID) {
    return this.activeMarkets.get(marketID);
  }

  public List<ITradeRequestMessage> updateMarket(Integer marketID,
      List<Integer> agents) {
    IMarket market = this.activeMarkets.get(marketID);
    market.tick();
    market.updateInnerInformation();
    this.whiteboard.postInnerInformation(marketID,
        this.activeMarkets.get(marketID).getPublicState());

    List<ITradeRequestMessage> tradeRequests =
        new LinkedList<ITradeRequestMessage>();
    for (Integer agentID : agents) {
      ITradeRequestMessage tRequest = market.constructTradeRequest(agentID); 
      // TODO: add the inner information here. This includes reserves, whatever else.
      //tRequest.addInformation(whiteboard)
      //for an initial trade request in the most basic case... what is it? 
      tradeRequests.add(tRequest);
    }
    return tradeRequests;
  }

  @Override
  public Map<Integer, IInformationMessage>
      constructInformationMessages(Integer marketID, List<Integer> agentIDs) {
    Map<Integer, IInformationMessage> informationMessages =
        new HashMap<Integer, IInformationMessage>();
    IMarketPublicState publicState =
        this.whiteboard.getOuterInformation(marketID);
    // TODO: somehow construct information messages from this public state.
    return null;
  }

  @Override
  public List<IAccountUpdate> finishMarket(Integer marketID) {
    List<IAccountUpdate> accountUpdates =
        this.activeMarkets.get(marketID).constructOrders();
    IMarket market = this.activeMarkets.get(marketID);
    market.updateOuterInformation();
    this.whiteboard.postOuterInformation(marketID,
        this.activeMarkets.get(marketID).getPublicState());
    return accountUpdates;
  }

  @Override
  public void finalizeMarket(Integer marketID) {
    this.activeMarkets.remove(marketID);
  }

  @Override
  public boolean marketOpen(Integer marketID) {
    return this.activeMarkets.get(marketID).isOpen();
  }

  @Override
  public boolean anyMarketsOpen() {
    for (Integer marketID : this.activeMarkets.keySet()) {
      if (this.activeMarkets.get(marketID).isOpen()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void reset() {
    this.activeMarkets.clear();
    this.whiteboard.clear();
    this.marketIndex = 0;
  }

}
