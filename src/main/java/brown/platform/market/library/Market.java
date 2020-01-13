package brown.platform.market.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.logging.library.AuctionLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;
import brown.platform.utils.Utils;


public class Market implements IMarket {

  private final Integer ID;
  private final IFlexibleRules RULES;
  private final IMarketState STATE;
  private final IMarketPublicState PUBLICSTATE;
  private final Set<Integer> AGENTS; 
  private final ICart TRADEABLES;

  private List<ITradeMessage> bids;

  // TODO: make the market remember its history in a memory-efficient way. 
  // make the state a remembering thing. 
  // at some point need to add the bids into the market state. 
  public Market(Integer ID, IFlexibleRules rules, IMarketState state,
      IMarketPublicState publicState, Set<Integer> agents, ICart tradeables) {
    this.ID = ID;
    this.RULES = rules;
    this.STATE = state;
    this.PUBLICSTATE = publicState;
    this.AGENTS = agents; 
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
    // ok... if the termination condition is that there are no bids, the allocation rule is not gonna do anything
    // because there are no bids. 
    // so what will do the allocation? 
    // the allocation rule has to use a history. 
    
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
    this.RULES.getActRule().setReserves(this.STATE, this.TRADEABLES);
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
    this.RULES.getTerminationCondition().checkTerminated(this.STATE, this.bids);
    return this.STATE.isOpen();
  }

  @Override
  public IMarketPublicState getPublicState() {
    return this.PUBLICSTATE;
  }
  
  @Override
  public IMarketPublicState getUnredactedPublicState() {
    return Utils.toPublicState(this.STATE);
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
  
  @Override
  public void updateTradeHistory() {
    this.STATE.addToTradeHistory(this.bids);
  }


}
