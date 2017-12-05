package brown.rules.paymentrules.library;



import brown.market.marketstate.IMarketState;
import brown.rules.paymentrules.IPaymentRule;

public class LemonadePaymentOld implements IPaymentRule {

  @Override
  public void setPayments(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPaymentType(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserve(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void permitShort(IMarketState state) {
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
