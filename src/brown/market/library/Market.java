package brown.market.library;

import java.util.List;

import brown.accounting.Ledger;
import brown.accounting.Order;
import brown.market.IMarket;
import brown.market.marketstate.IMarketState;
import brown.market.preset.AbsMarketPreset;
import brown.messages.library.Bid;
import brown.messages.library.GameReport;
import brown.messages.library.TradeRequest;
import brown.rules.activityrules.IActivityRule;
import brown.rules.allocationrules.IAllocationRule;
import brown.rules.irpolicies.IInformationRevelationPolicy;
import brown.rules.paymentrules.IPaymentRule;
import brown.rules.queryrules.IQueryRule;
import brown.rules.terminationconditions.ITerminationCondition;

public class Market implements IMarket {

  private final IPaymentRule PRULE;
  private final IAllocationRule ARULE;
  private final IQueryRule QRULE;
  private final IActivityRule ACTRULE;
  private final IInformationRevelationPolicy INFOPOLICY;
  private final ITerminationCondition TCONDITION;
  private final IMarketState STATE;
  
  public Market(AbsMarketPreset rules, IMarketState state) {
    this.PRULE = rules.pRule;
     this.ARULE = rules.aRule;
     this.QRULE = rules.qRule;
     this.ACTRULE = rules.actRule;
     this.INFOPOLICY = rules.infoPolicy;
     this.TCONDITION = rules.tCondition;
     this.STATE = state;
     
     //set up all of the conditions and stuff needed here.
     this.ARULE.setBundleType(this.STATE);
     this.ARULE.getAllocationType(this.STATE);
     this.PRULE.setPaymentType(this.STATE);
 }
  
  @Override
  public Integer getID() {
    return this.STATE.getID();
  }

  @Override
  public TradeRequest constructTradeRequest(Integer ID, Ledger ledger) {
    //do something with the IR policy.
    this.QRULE.makeChannel(STATE, ledger);
    TradeRequest request = this.STATE.getTRequest();
    return request;
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    TCONDITION.tisOver(this.STATE);
    return this.STATE.getTOver();
  }

  @Override
  public boolean handleBid(Bid bid) {
    this.ACTRULE.isAcceptable(this.STATE, bid); 
    if(this.STATE.getAcceptable()) {
        STATE.addBid(bid);
    }
    return this.STATE.getAcceptable();
  }

  @Override
  public List<Order> getOrders() {
    this.ARULE.setAllocation(this.STATE);
    // TODO Auto-generated method stub
//    System.out.println("B"); 
//    System.out.println(this.STATE.getPayments().size()); 
//    System.out.println("B"); 
    return this.STATE.getPayments();
  }

  @Override
  public void tick(long time) {
    this.STATE.setTime(time);
  }

  @Override
  public GameReport getReport() {
    this.ARULE.setReport(STATE);
    return this.STATE.getReport(); 
  } 
  
}