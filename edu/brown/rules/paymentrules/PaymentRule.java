package brown.rules.paymentrules;


public interface PaymentRule {

  //thi can use the market internal state for all of its information. 
  
  
  public void getPayments();
  
  public void permitShort();
  
  
//	public List<Order> getPayments(MarketInternalState state);
//	
//	public Map<BidBundle, Set<Asset>> getPayments(Map<Integer, Set<Asset>> allocations, Set<Bid> bids);
//	
//	public List<Order> getPayments(Map<Integer, Set<Asset>> allocations);
//
//	public PaymentType getPaymentType();
//
//	public BidBundle getReserve();
//	
//	public boolean permitShort();


}
