package brown.markets;

import java.util.List;

import brown.assets.accounting.Ledger;
import brown.assets.accounting.Order;
import brown.marketinternalstates.MarketInternalState;
import brown.markets.presets.MarketPreset;
import brown.messages.auctions.Bid;
import brown.messages.markets.GameReport;
import brown.messages.markets.TradeRequest;
import brown.rules.activityrules.ActivityRule;
import brown.rules.allocationrules.AllocationRule;
import brown.rules.irpolicies.InformationRevelationPolicy;
import brown.rules.paymentrules.PaymentRule;
import brown.rules.queryrules.QueryRule;
import brown.rules.terminationconditions.TerminationCondition;

public class Market implements IMarket {

  private final PaymentRule PRULE;
  private final AllocationRule ARULE;
  private final QueryRule QRULE;
  private final ActivityRule ACTRULE;
  private final InformationRevelationPolicy INFOPOLICY;
  private final TerminationCondition TCONDITION;
  private final MarketInternalState STATE;
  
  public Market(MarketPreset rules, MarketInternalState state) {
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