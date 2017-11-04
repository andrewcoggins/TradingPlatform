package brown.markets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.assets.accounting.Ledger;
import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.markets.presets.MarketPreset;
import brown.messages.auctions.Bid;
import brown.messages.markets.TradeRequest;
import brown.rules.activityrules.ActivityRule;
import brown.rules.allocationrules.AllocationRule;
import brown.rules.irpolicies.InformationRevelationPolicy;
import brown.rules.paymentrules.PaymentRule;
import brown.rules.queryrules.QueryRule;
import brown.rules.terminationconditions.TerminationCondition;
import brown.tradeables.Asset;

public class LemonadeMarket implements IMarket {
  
  private final PaymentRule PRULE;
  private final AllocationRule ARULE;
  
  private final QueryRule QRULE;
  private final ActivityRule ACTRULE;
  private final InformationRevelationPolicy INFOPOLICY;
  
  private final TerminationCondition TCONDITION;
  
  private final MarketInternalState STATE;
  private int lastTerm = 0;
  private int term = 0;

  
  public LemonadeMarket(MarketPreset rules, MarketInternalState state) {
     this.PRULE = rules.pRule;
      this.ARULE = rules.aRule;
      this.QRULE = rules.qRule;
      this.ACTRULE = rules.actRule;
      this.INFOPOLICY = rules.infoPolicy;
      this.TCONDITION = rules.tCondition;
      this.STATE = state;
      this.STATE.setReserve(this.PRULE.getReserve());
  }
  
  /**
   * Pass through market ID
   * @return
   */
  public Integer getID() {
    return this.STATE.getID();
  }
  
  /**
   * Constructs the trade request
   * @param ID : agent who it's for
   * @param ledger : past transactions for market
   * @return TradeRequest for ID
   */

  public TradeRequest constructTradeRequest(Integer ID, Ledger ledger) {
    if (this.term != this.lastTerm) {
      this.STATE.setAllocation(this.ARULE.getAllocation(this.STATE));
      this.STATE.setPayments(this.PRULE.getPayments(this.STATE));
      this.lastTerm = this.term;
    }
    System.out.println(ID);
    System.out.println(this.STATE);
    System.out.println(ID);
    
    this.QRULE.makeChannel(ledger, this.PRULE.getPaymentType(),
        this.INFOPOLICY.handleInfo(ID, this.STATE));
    
    return this.STATE.getChannel();
    
  }
  
  /**
   * Tests if the market has ended
   * @return true if ended
   */
  public boolean isOver() {
    return this.TCONDITION.isOver(this.STATE);
  }
  
  /**
   * Whenever a new bid comes in
   * @param bid
   */
  public boolean handleBid(Bid bid) {
    if (this.ACTRULE.isAcceptable(this.STATE, bid)) {
      this.STATE.addBid(bid);
      return true;
    }
    return false;
  }
  
  /**
   * Get the orders when the
   * market ends
   * @return List<Order> to process
   */
  public List<Order> getOrders() {
    if (!this.isOver())  {
      return new LinkedList<Order>();
    }
    

    @SuppressWarnings("unchecked")
    Map<Integer, Set<Asset>> newState = 
        this.ARULE.getAllocations((Set<Bid>) STATE.getBids(), STATE.getTradeables());
    Map<BidBundle, Set<Asset>> someBids = this.PRULE.getPayments(newState, null);
    List<Order> toReturn = new ArrayList<Order>();
    for (BidBundle b : someBids.keySet()) {
      for(Asset a : someBids.get(b)){ 
        toReturn.add(new Order(a.getAgentID(), null, b.getCost(), a.getCount(), a));
      }
    }
    { 
      //this.STATE.setAllocation(newState);
    }
    //List<Order> newPayments = this.PRULE.getPayments(this.STATE);
    //this.STATE.setPayments(newPayments);
    return toReturn;
  }
  
  

  /**
   * Ticks the market for clock auctions
   * @param time
   */
  public void tick(long time) {
    this.term++;
    this.STATE.tick(time);
  }


}