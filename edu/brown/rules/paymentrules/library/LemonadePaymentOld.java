package brown.rules.paymentrules.library;



import brown.marketinternalstates.MarketInternalState;
import brown.rules.paymentrules.PaymentRule;

public class LemonadePaymentOld implements PaymentRule {

  @Override
  public void getPayments(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPaymentType(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserve(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void permitShort(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

//  @Override
//  public Map<BidBundle, Set<Asset>>
//  //wtf look at this.
//      getPayments(Map<Integer, Set<Asset>> allocations, Set<Bid> bids) {
//    Map<BidBundle, Set<Asset>> payments = new HashMap<BidBundle, Set<Asset>>();
//    for (Map.Entry<Integer, Set<Asset>> alloc : allocations.entrySet()) {
//      payments.put(new SimpleBidBundle(null), alloc.getValue());
//      //payments.put(new SimpleBidBundle(0, alloc.getKey(), BundleType.Simple), alloc.getValue());
//    }
//    return null;
//  }
//
//  @Override
//  public PaymentType getPaymentType() {
//    return PaymentType.Lemonade;
//  }
//
//  @Override
//  public boolean permitShort() {
//    return true;
//  } 
//  
//  @Override
//  public BidBundle getReserve() {
//    return null;
//  }
//  
//  @Override
//  public List<Order> getPayments(MarketInternalState state) {
//    return null;
//  }
//
//
//  @Override
//  public List<Order> getPayments(Map<Integer, Set<Asset>> allocations) {
//    // TODO Auto-generated method stub
//    return null;
//  }

}
