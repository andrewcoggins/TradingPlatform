package brown.market.library;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import brown.bidbundle.IBidBundle;
import brown.logging.Logging;
import brown.market.IMarket;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.market.preset.AbsMarketPreset;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IActivityRule;
import brown.rules.IAllocationRule;
import brown.rules.IGroupingRule;
import brown.rules.IInformationRevelationPolicy;
import brown.rules.IInnerTC;
import brown.rules.IOuterTC;
import brown.rules.IPaymentRule;
import brown.rules.IQueryRule;
import brown.rules.IRecordKeepingRule;
import brown.value.valuation.IValuation;

public class Market implements IMarket {

  private final IPaymentRule PRULE;
  private final IAllocationRule ARULE;
  private final IQueryRule QRULE;
  private final IGroupingRule GRULE;
  private final IActivityRule ACTRULE;
  private final IInformationRevelationPolicy IRPOLICY;
  private final IInnerTC ITCONDITION;
  private final IOuterTC OTCONDITION; 
  private final IMarketState STATE;
  private final IRecordKeepingRule rRule;
  
  public Market(AbsMarketPreset rules, IMarketState state) {
    this.PRULE = rules.pRule;
    this.ARULE = rules.aRule;
    this.QRULE = rules.qRule;
    this.GRULE = rules.gRule;
    this.ACTRULE = rules.actRule;
    this.IRPOLICY = rules.infoPolicy;
    this.ITCONDITION = rules.innerTCondition;
    this.OTCONDITION = rules.outerTCondition;
    this.STATE = state;
    this.rRule = rules.rRule;
 }
  
  @Override
  public Integer getMarketID() {
    return this.STATE.getID();
  }

  public TradeRequestMessage constructTradeRequest(Integer ID) {
    //no idea why ledgers are part of the trade request -- they should be sent as market updates!
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
  public boolean isInnerOver() {
    ITCONDITION.innerTerminated(this.STATE);
    return this.STATE.getInnerOver();
  }
  
  @Override
  public boolean isOverOuter() {
    OTCONDITION.outerTerminated(this.STATE);
    return this.STATE.getOuterOver();
  } 

  @Override
  public void tick() {
    this.STATE.tick();
  }
  
  @Override
  public void setReserves() {
    this.ACTRULE.setReserves(this.STATE); 
  }
  
  @Override
  public PrevStateInfo constructSummaryState() {
    this.IRPOLICY.constructSummaryState(this.STATE);
    return this.STATE.getSummaryState();
  }
  
  @Override
  public void resetInternalState() {
    this.ACTRULE.reset();
    this.ARULE.reset();
    this.IRPOLICY.reset();
    this.ITCONDITION.reset();
    this.PRULE.reset();
    this.QRULE.reset();
    this.GRULE.reset();
    this.STATE.reset();
    this.STATE.incrementOuter();
  }
  
  @Override
  public void setGroupings(List<Integer> agents) {
    this.GRULE.setGrouping(this.STATE, agents);
  }
  
  @Override
  public void record(Map<Integer,IValuation> privateVals) throws IOException {    
    this.rRule.record(this.STATE, privateVals);
  }
  
  @Override
  public void close() {
    this.STATE.close(); 
  }
  
  public boolean isOpen() {
    return this.STATE.isOpen(); 
  }
}
