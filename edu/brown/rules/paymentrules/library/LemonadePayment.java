package brown.rules.paymentrules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.rules.paymentrules.PaymentRule;
import brown.rules.paymentrules.PaymentType;
import brown.tradeables.Asset;

public class LemonadePayment implements PaymentRule {

  @Override
  public Map<BidBundle, Set<Asset>>
  //wtf look at this.
      getPayments(Map<Integer, Set<Asset>> allocations, Set<Bid> bids) {
    Map<BidBundle, Set<Asset>> payments = new HashMap<BidBundle, Set<Asset>>();
    for (Map.Entry<Integer, Set<Asset>> alloc : allocations.entrySet()) {
      payments.put(new SimpleBidBundle(null), alloc.getValue());
      //payments.put(new SimpleBidBundle(0, alloc.getKey(), BundleType.Simple), alloc.getValue());
    }
    return null;
  }

  @Override
  public PaymentType getPaymentType() {
    return PaymentType.Lemonade;
  }

  @Override
  public boolean permitShort() {
    return true;
  } 
  
  @Override
  public BidBundle getReserve() {
    return null;
  }
  
  @Override
  public List<Order> getPayments(MarketInternalState state) {
    return null;
  }

}
