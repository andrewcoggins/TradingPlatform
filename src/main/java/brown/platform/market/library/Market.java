package brown.platform.market.library;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;

/**
 * Common implementation of IMarket.
 *
 * @author acoggins
 */
public class Market implements IMarket {
  
  private final IFlexibleRules RULES; 
  
  private final IMarketState STATE;
  private final IMarketPublicState PUBLICSTATE;

  /**
   * @param rules
   * @param state
   * TODO: history
   */
  public Market(IFlexibleRules rules, IMarketState state, IMarketPublicState publicState) {
    this.RULES = rules; 
    
    this.STATE = state;
    this.PUBLICSTATE = publicState;
  }
  
  // Make MarketID a field
  @Override
  public Integer getMarketID() {
    return this.STATE.getID();
  }

  // Processing bids is a four step process:
  // 1. Send trade requests via Query Rule
  // 2. Check acceptability via Activity Rule
  // 3. Find allocation and payments (via these rules)
  // 4. Send game report (via IR policy)
  public TradeRequestMessage constructTradeRequest(Integer ID) {
    // no idea why ledgers are part of the trade request -- they should be sent as market updates!
    this.RULES.getQRule().makeChannel(STATE);
    TradeRequestMessage request = this.STATE.getTRequest();
    return request;
  }

  // this looks like it is checking validity, not processing the bids
  // name seems misleading
  public boolean processBid(ITradeMessage bid) {
//    this.ACTRULE.isAcceptable(this.STATE, bid); 
//    // why are we checking isOpen here? should check this much earlier!
//    if (this.STATE.getAcceptable() && this.STATE.isOpen()) {
//      STATE.addBid(bid);
//    }
    return this.STATE.getAcceptable();
  }

  public List<IAccountUpdate> constructOrders() {
    // Set allocation and payment
    this.RULES.getARule().setAllocation(this.STATE);
    this.RULES.getPRule().setOrders(this.STATE); // setPayment

    // Construct orders from allocation and payments
    // why no? return this.STATE.getAllocation();
    //return this.STATE.getPayments();
    return null; 
  }

  @Override 
  // Make sure this is called after constructOrders
  public Map<Integer, List<IInformationMessage>> constructReport() {
    this.RULES.getIRPolicy().setReport(this.STATE);
    return null;
  }
  
  
  // Move to MarketState
  // For example, this needs to be done in the logic before getAcceptable,
  // e.g., once per iteration in an Open Outcry auction
  @Override
  public void setReserves() {
    this.RULES.getActRule().setReserves(this.STATE); 
  }
  
  // This stays, b/c Bids are being moved out of MarketState
  // But all that logic might end up somewhere else
  @Override
  public void clearBidCache() {
    this.STATE.clearBids();
  }
  
  
  // The remaining logic pertains to the Termination Condition
  @Override
  public void tick() {
    this.STATE.tick();
  }
  
  
  @Override
  public boolean isOpen() {
    return this.STATE.isOpen(); 
  }  
  
  @Override
  public void close() {
    this.STATE.close(); 
  }

  @Override
  public IMarketPublicState getPublicState() {
    return this.PUBLICSTATE;
  }

}
