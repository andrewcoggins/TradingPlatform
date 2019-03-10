package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.managers.IMarketManager;
import brown.platform.market.IMarket;
import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarketRules;
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
    this.activeMarkets = new HashMap<Integer, IMarket>(); 
    this.lock = false;
    this.whiteboard = new Whiteboard(); 
    this.marketIndex = 0; 
  }

  @Override
  public void createSimultaneousMarket(List<IMarketRules> marketRules,
      List<List<String>> marketTradeableNames, Map<String, List<ITradeable>> allTradeables) {
    if (!this.lock) {
      List<Map<String, List<ITradeable>>> marketTradeables = new LinkedList<Map<String, List<ITradeable>>>();
      for (List<String> tList : marketTradeableNames) {
        Map<String, List<ITradeable>> singleMarketTradeables = new HashMap<String, List<ITradeable>>();
        for (String tName : tList) {
          singleMarketTradeables.put(tName, allTradeables.get(tName));
        }
        marketTradeables.add(singleMarketTradeables);
      }
      IMarketBlock marketBlock = new SimultaneousMarket(marketRules, marketTradeables);
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
  public List<IAccountUpdate> finishMarket(Integer marketID) {
    try {
      IMarketBlock currentBlock = this.allMarkets.get(this.marketIndex); 
      List<IMarketRules> mRules = currentBlock.getMarkets(); 
      List<Map<String, List<ITradeable>>> mTradeables = currentBlock.getMarketTradeables(); 
      
      
      // going to create the markets, and then garbage collection will handle the rest I assume. 
      
    } catch (IndexOutOfBoundsException e) {
      ErrorLogging.log("ERROR: Market index out of range! Index: " + this.marketIndex.toString() + 
          " market block length: " + Integer.toString(this.allMarkets.size()));
      throw e; 
    }
    return null;
  }
  
  public List<ITradeRequestMessage> updateMarket(Integer marketID, List<Integer> agents) {
    return null; 
  }

  @Override
  public void handleTradeMessage(ITradeMessage message) {
  }


  @Override
  public void reset() {
  }

  @Override
  public Integer getNumMarketBlocks() {
    return this.allMarkets.size();
  }

  @Override
  public List<IMarket> getCurrentMarkets() {
    return new LinkedList<IMarket>(this.activeMarkets.values()); 
  }

  @Override
  public void finalizeMarket(Integer marketID) {
    // TODO: put market information in whiteboard.
    this.activeMarkets.remove(marketID); 
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
  public Map<Integer, IInformationMessage>
      constructInformationMessages(Integer marketID) {
    // TODO Auto-generated method stub
    return null;
  }


}
