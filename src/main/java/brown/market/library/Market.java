package brown.market.library;

import java.util.List;

import brown.accounting.library.Ledger;
import brown.market.IMarket;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Order;
import brown.market.preset.AbsMarketPreset;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.rules.activityrules.IActivityRule;
import brown.rules.allocationrules.IAllocationRule;
import brown.rules.irpolicies.IInformationRevelationPolicy;
import brown.rules.paymentrules.IPaymentRule;
import brown.rules.queryrules.IQueryRule;
import brown.rules.terminationconditions.IInnerTC;
import brown.rules.terminationconditions.IOuterTC;

public class Market implements IMarket {

  private final IPaymentRule PRULE;
  private final IAllocationRule ARULE;
  private final IQueryRule QRULE;
  private final IActivityRule ACTRULE;
  private final IInformationRevelationPolicy IRPOLICY;
  private final IInnerTC ITCONDITION;
  private final IOuterTC OTCONDITION; 
  private final ICompleteState STATE;
  
  public Market(AbsMarketPreset rules, ICompleteState state) {
    this.PRULE = rules.pRule;
    this.ARULE = rules.aRule;
    this.QRULE = rules.qRule;
    this.ACTRULE = rules.actRule;
    this.IRPOLICY = rules.infoPolicy;
    this.ITCONDITION = rules.innerTCondition;
    this.OTCONDITION = rules.outerTCondition;
    this.STATE = state;
 }
  
  @Override
  public Integer getID() {
    return this.STATE.getID();
  }

  public TradeRequestMessage constructTradeRequest(Integer ID) {
    //no idea why ledgers are part of the trade request -- they should be sent as market updates!
    Ledger ledger = new Ledger(this.getID());
    for (Order o : getOrders()) {
      ledger.add(o.toTransaction());
    }
    this.QRULE.makeChannel(STATE, ledger);
    TradeRequestMessage request = this.STATE.getTRequest();
    return request;
  }

  // this looks like it is checking validity, not processing the bids
  // name seems misleading
  public boolean handleBid(TradeMessage bid) {
    this.ACTRULE.isAcceptable(this.STATE, bid); 
    if (this.STATE.getAcceptable()) {
      STATE.addBid(bid);
    }
    return this.STATE.getAcceptable();
  }

  // this seems more like constructOrders
  public List<Order> getOrders() {
    // Set allocation and payment
    this.ARULE.setAllocation(this.STATE);
    // construct orders
    this.PRULE.setOrders(this.STATE); // setPayment

    // Construct orders from allocation and payments
    return this.STATE.getMarketState().getPayments().getOrders();
  }

  // maybe this is constructGameReport
  // i'm worried about setAllocation and setOrders potentially being called twice in a row
  public GameReportMessage getReport() {
    // Set allocation and payment
    this.ARULE.setAllocation(this.STATE);
    this.PRULE.setOrders(this.STATE);
    // Construct orders from allocation and payments

    this.IRPOLICY.setReport(this.STATE);
    return this.STATE.getReport();
  }

  public boolean isOver() {
    ITCONDITION.innerTerminated(this.STATE);
    return this.STATE.getInnerOver();
  }
  
  public boolean isOverOuter() {
    OTCONDITION.outerTerminated(this.STATE);
    return this.STATE.getOuterOver();
  } 

  public void tick(long time) {
    this.STATE.tick(time);
  }

  //why clear so little of the market state? is there not more to clear?
  public void clearState() { 
    this.STATE.clearBids();
    this.STATE.clearOrders();
  }
  
}
