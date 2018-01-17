package brown.market.library;

import java.util.List;

import abrown.misc.Allocation;
import brown.accounting.Ledger;
import brown.accounting.bidbundle.IBidBundle;
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
     
     //set up all of the conditions and stuff needed here.
//     this.ARULE.setBundleType(this.STATE);
//     this.ARULE.getAllocationType(this.STATE);
//     this.PRULE.setPaymentType(this.STATE);
 }
  
  @Override
  public Integer getID() {
    return this.STATE.getID();
  }

  // constructs a trade request
  @Override
  public TradeRequestMessage constructTradeRequest(Integer ID) {
    //do something with the IR policy.
    Allocation alloc = this.STATE.getAllocation();
    if(alloc != null) {
      this.QRULE.makeChannel(STATE, new Ledger(this.getID(), (Allocation) alloc));
      TradeRequestMessage request = this.STATE.getTRequest();
      return request;
    }
     this.QRULE.makeChannel(STATE, new Ledger(this.getID()));
     TradeRequestMessage request = this.STATE.getTRequest();
     return request;
  }

  // asks if the game in a sequence of games is
  // over per the termination condition
  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    ITCONDITION.innerTerminated(this.STATE);
    return this.STATE.getInnerOver();
  }
  
  // asks if the entire sequence of games is 
  // over per the outer termination condition.
  @Override
  public boolean isOverOuter() {
    // TODO Auto-generated method stub
    OTCONDITION.outerTerminated(this.STATE);
    //clear the orders. Maybe clear other things too.
    return this.STATE.getOuterOver();
  } 

  // handles a bid from an agent. The activity rule determines if it is 
  // to be accepted or not. 
  @Override
  public boolean handleBid(TradeMessage bid) {
    this.ACTRULE.isAcceptable(this.STATE, bid); 
    if(this.STATE.getAcceptable()) {
        STATE.addBid(bid);
    }
    return this.STATE.getAcceptable();
  }

  @Override
  public List<Order> getOrders() {
    this.ARULE.setAllocation(this.STATE);
    //payments are being set in allocation rule. 
    //what we should have is: 
    this.PRULE.setPayments(this.STATE);
    return this.STATE.getPayments();
  }

  // increments time. 
  @Override
  public void tick(long time) {
    this.STATE.tick(time);
  }

  @Override
  public void clearState() { 
    this.STATE.clearBids();
    this.STATE.clearOrders();
  }
  
  @Override 
  public GameReportMessage getReport() {
    this.ARULE.setAllocation(this.STATE);
    this.IRPOLICY.setReport(this.STATE);
    return this.STATE.getReport();
//    Allocation alloc = this.STATE.getAllocation(); 
//    return new GameReport(new Ledger(this.getID(), alloc));
  }
  
}