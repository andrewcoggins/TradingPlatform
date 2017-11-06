package brown.markets;

import java.util.List;

import brown.assets.accounting.Ledger;
import brown.assets.accounting.Order;
import brown.messages.auctions.Bid;
import brown.messages.markets.TradeRequest;


/*
 * A trading environment consists of a set of markets
 * together with a set of agents. This class dictates a market
 * in which agents will bid.
 */
public class MarketOld implements IMarket {

  @Override
  public Integer getID() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TradeRequest constructTradeRequest(Integer ID, Ledger ledger) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean handleBid(Bid bid) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Order> getOrders() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void tick(long time) {
    // TODO Auto-generated method stub
    
  }
//	private final PaymentRule PRULE;
//	private final AllocationRule ARULE;
//	
//	private final QueryRule QRULE;
//	private final ActivityRule ACTRULE;
//	private final InformationRevelationPolicy INFOPOLICY;
//	
//	private final TerminationCondition TCONDITION;
//	
//	private final MarketInternalState STATE;
//	private int lastTerm = 0;
//	private int term = 0;
//	
//	public MarketOld(PaymentRule pRule, AllocationRule aRule, QueryRule qRule,
//			InformationRevelationPolicy infoPolicy,  
//			TerminationCondition tCondition, ActivityRule actRule, MarketInternalState startingState) {
//		this.PRULE = pRule;
//		this.ARULE = aRule;
//		this.QRULE = qRule;
//		this.ACTRULE = actRule;
//		this.INFOPOLICY = infoPolicy;
//		this.TCONDITION = tCondition;
//		this.STATE = startingState;
//		
//		this.STATE.setReserve(this.PRULE.getReserve());
//	}
//	
//	public MarketOld(MarketPreset rules, MarketInternalState state) {
//	   this.PRULE = rules.pRule;
//	    this.ARULE = rules.aRule;
//	    this.QRULE = rules.qRule;
//	    this.ACTRULE = rules.actRule;
//	    this.INFOPOLICY = rules.infoPolicy;
//	    this.TCONDITION = rules.tCondition;
//	    this.STATE = state;
//	    this.STATE.setReserve(this.PRULE.getReserve());
//	}
//	
//	/**
//	 * Pass through market ID
//	 * @return
//	 */
//	public Integer getID() {
//		return this.STATE.getID();
//	}
//	
//	/**
//	 * Constructs the trade request
//	 * @param ID : agent who it's for
//	 * @param ledger : past transactions for market
//	 * @return TradeRequest for ID
//	 */
//
//	public TradeRequest constructTradeRequest(Integer ID, Ledger ledger) {
//		if (this.term != this.lastTerm) {
//			this.STATE.setAllocation(this.ARULE.getAllocation(this.STATE));
//			this.STATE.setPayments(this.PRULE.getPayments(this.STATE));
//			this.lastTerm = this.term;
//		}
//		System.out.println(ID);
//		System.out.println(this.STATE.getAllocation());
//		System.out.println(ID);
//		
//		return this.QRULE.constructChannel(ledger, this.PRULE.getPaymentType(),
//				this.INFOPOLICY.handleInfo(ID, this.STATE));
//	}
//	
//	/**
//	 * Tests if the market has ended
//	 * @return true if ended
//	 */
//	public boolean isOver() {
//		return this.TCONDITION.isOver(this.STATE);
//	}
//	
//	/**
//	 * Whenever a new bid comes in
//	 * @param bid
//	 */
//	public boolean handleBid(Bid bid) {
//		if (this.ACTRULE.isAcceptable(this.STATE, bid)) {
//			this.STATE.addBid(bid);
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * Get the orders when the
//	 * market ends
//	 * @return List<Order> to process
//	 */
//	public List<Order> getOrders() {
//		if (!this.isOver())  {
//			return new LinkedList<Order>();
//		}
//		
//
//		BidBundle newState = this.ARULE.getAllocation(this.STATE);
//		this.STATE.setAllocation(newState);
//		List<Order> newPayments = this.PRULE.getPayments(this.STATE);
//		this.STATE.setPayments(newPayments);
//		return this.STATE.getPayments();
//	}
//	
//	
//
//	/**
//	 * Ticks the market for clock auctions
//	 * @param time
//	 */
//	public void tick(long time) {
//		this.term++;
//		this.STATE.tick(time);
//	}

}
