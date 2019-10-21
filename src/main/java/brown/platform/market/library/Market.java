package brown.platform.market.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.logging.library.AuctionLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;


public class Market implements IMarket {

  private final Integer ID;
  private final IFlexibleRules RULES;
  private final IMarketState STATE;
  private final IMarketPublicState PUBLICSTATE;
  private final ICart TRADEABLES;

  private List<ITradeMessage> bids;

  
  public Market(Integer ID, IFlexibleRules rules, IMarketState state,
      IMarketPublicState publicState, ICart tradeables) {
    this.ID = ID;
    this.RULES = rules;
    this.STATE = state;
    this.PUBLICSTATE = publicState;
    this.TRADEABLES = tradeables;
    this.bids = new LinkedList<ITradeMessage>();
  }

  @Override
  public Integer getMarketID() {
    return this.ID;
  }


  public ITradeRequestMessage constructTradeRequest(Integer agentID) {
    this.RULES.getQRule().makeTradeRequest(ID, STATE, TRADEABLES, bids, agentID);
    TradeRequestMessage request = this.STATE.getTRequest();
    return request;
  }

  public boolean processBid(ITradeMessage bid) {
    this.RULES.getActRule().isAcceptable(this.STATE, bid, this.bids,
        this.TRADEABLES);
    boolean acceptable = this.STATE.getAcceptable();
    if (acceptable) {
      this.bids.add(bid);
    } 
    return acceptable;
  }

  public List<IAccountUpdate> constructAccountUpdates() {
    AuctionLogging.log("Bids submitted to Auction " + this.ID.toString() + ": " + this.bids);
    this.RULES.getARule().setAllocation(this.STATE, this.bids);
    AuctionLogging.log("Allocations from Auction " + this.ID.toString() + ": " + this.STATE.getAllocation());
    this.RULES.getPRule().setOrders(this.STATE, this.bids);
    AuctionLogging.log("Payments from Auction " + this.ID.toString() + ": " + this.STATE.getPayments());
    return this.STATE.getPayments();
  }


  // Move to MarketState
  // For example, this needs to be done in the logic before getAcceptable,
  // e.g., once per iteration in an Open Outcry auction
  @Override
  public void setReserves() {
    this.RULES.getActRule().setReserves(this.STATE);
  }

  
  @Override
  public void clearBidCache() {
    this.bids.clear();
  }

  @Override
  public void tick() {
    this.STATE.tick();
  }
  
  @Override
  public Integer getTimestep() {
    return this.STATE.getTicks();
  }

  @Override
  public boolean isOpen() {
    this.RULES.getTerminationCondition().checkTerminated(this.STATE);
    return this.STATE.isOpen();
  }

  @Override
  public IMarketPublicState getPublicState() {
    return this.PUBLICSTATE;
  }

  @Override
  public void updateOuterInformation() {
    this.RULES.getIRPolicy().updatePublicState(this.STATE, this.PUBLICSTATE);
  }

  @Override
  public void updateInnerInformation() {
    this.RULES.getInnerIRPolicy().updatePublicState(this.STATE,
        this.PUBLICSTATE);
  }

}
