package brown.marketinternalstates;

import java.util.List;
import java.util.Set;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.messages.auctions.Bid;
import brown.messages.markets.TradeRequest;
import brown.rules.paymentrules.PaymentType;
import brown.tradeables.Asset;

/**
 * stores the internal state of a market as 
 * bidding is occurring.
 * @author acoggins
 *
 */
public interface MarketInternalState {

	public Integer getID();


	public void addBid(Bid bid);


	//public void setAllocation(BidBundle cleanedAlloc);
	

	public void setPayments(List<Order> payments);
	

	public void setReserve(BidBundle reserveBundle);
	
	
	
	
	public List<Bid> getBids();

	
	public Set<Asset> getTradeables();

	


	public void tick(long time);


	public int getTicks();


	public void clearBids();


	public double getIncrement();


	public void setMaximizingRevenue(boolean b);
	

	public boolean isMaximizingRevenue();


	public int getEligibility();
	
	
	/// new stuff.
	//need to store a payment type. 
	//need to store datatype for payment.
	
	//query rule
	public void setChannel(TradeRequest aRequest);
	
	public TradeRequest getChannel();
	//
	
	
	//payment rule. 
	
	 public List<Order> getPayments();
	 
  public PaymentType getPaymentType();

  public BidBundle getReserve();
 
  public boolean permitShort();
  
  
  //allocation rule.
  
  //store a bidbundle. 
  public BidBundle getAllocation();
  
  public BundleType getBundleType();
  
  public boolean isPrivate();
  
  public boolean isOver();
  
  //anon policy.
  
  //may take a little bit of creativity.
  
  
  //activity rule.
  
  public boolean isAcceptable();
  
  
  //tcondition 
  
  public boolean tisOver();

}
