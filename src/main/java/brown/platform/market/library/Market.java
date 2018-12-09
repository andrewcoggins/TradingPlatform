package brown.platform.market.library;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IGroupingRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.IRecordKeepingRule;
import brown.auction.value.valuation.IValuation;
import brown.platform.accounting.library.Order;
import brown.platform.market.IMarket;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.TradeMessage;
import brown.platform.messages.library.TradeRequestMessage;
import brown.platform.market.IHistory;

/**
 * Common implementation of IMarket.
 *
 * @author acoggins
 */
public class Market implements IMarket {

  // TODO: private final IFlexibleRules RULES;
  // delete all of these individual rule variables
  private final IPaymentRule PRULE;
  private final IAllocationRule ARULE;
  private final IQueryRule QRULE;
  private final IActivityRule ACTRULE;
  private final IInformationRevelationPolicy IRPOLICY;
  private final ITerminationCondition ITCONDITION;
  
  private final IMarketState STATE;
  private final IMarketState HISTORY;

  /**
   * @param rules
   * @param state
   * TODO: history
   */
  public Market(AbsMarketRules rules, IMarketState state, IHistory history) {
    // TODO: this.RULES = rules;
    // delete all of these individual assignments of rules
    this.PRULE = rules.pRule;
    this.ARULE = rules.aRule;
    this.QRULE = rules.qRule;
    this.ACTRULE = rules.actRule;
    this.IRPOLICY = rules.infoPolicy;
    this.ITCONDITION = rules.tCondition; 
    
    this.STATE = state;
    this.HISTORY = history;
  }
  
  // Make MarketID a field
  @Override
  public Integer getMarketID() {
    return this.STATE.getID();
  }

  
  public TradeRequestMessage constructTradeRequest(Integer ID) {
    // no idea why ledgers are part of the trade request -- they should be sent as market updates!
    this.QRULE.makeChannel(STATE);
    TradeRequestMessage request = this.STATE.getTRequest();
    return request;
  }

  // this looks like it is checking validity, not processing the bids
  // name seems misleading
  public boolean handleBid(TradeMessage bid) {
    this.ACTRULE.isAcceptable(this.STATE, bid); 
    if (this.STATE.getAcceptable() && this.STATE.isOpen()) {
      STATE.addBid(bid);
    }
    return this.STATE.getAcceptable();
  }

  // this seems more like constructOrders
  public List<Order> constructOrders() {
    // Set allocation and payment
    this.ARULE.setAllocation(this.STATE);
    // construct orders
    this.PRULE.setOrders(this.STATE); // setPayment

    // Construct orders from allocation and payments
    return this.STATE.getPayments();
  }

  @Override 
  // Make sure this is called after constructOrders
  public Map<Integer, List<GameReportMessage>> constructReport() {
    this.IRPOLICY.setReport(this.STATE);
    return this.STATE.getReport();
  }
  
  @Override
  public void setReserves() {
    this.ACTRULE.setReserves(this.STATE); 
  }
  
  @Override
  public void clearBidCache() {
    this.STATE.clearBids();
  }
  
  
  
  @Override
  public void tick() {
    this.STATE.tick();
  }
  
  // do we really need isOver and isOpen?
  @Override
  public boolean isOver() {
    ITCONDITION.isTerminated(this.STATE);
    return this.STATE.getOver();
  }
  
  @Override
  public boolean isOpen() {
    return this.STATE.isOpen(); 
  }  
  
  @Override
  public void close() {
    this.STATE.close(); 
  }
  
  
}
