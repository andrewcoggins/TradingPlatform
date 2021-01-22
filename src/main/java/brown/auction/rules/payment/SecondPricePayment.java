package brown.auction.rules.payment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IPaymentRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.logging.library.ErrorLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;

public class SecondPricePayment extends AbsRule implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state, List<ITradeMessage> messages) {
    Map<Integer, List<ICart>> allocation = state.getAllocation();
    
    Map<ICart, Double> highest = new HashMap<ICart, Double>();
    Map<ICart, Double> secondHighest = new HashMap<ICart, Double>();

    for (ITradeMessage tradeMessage : messages) { 
      IBidBundle bundle = (IBidBundle) tradeMessage.getBid();
      Map<ICart, Double> cartBids = bundle.getBids();
      for (Map.Entry<ICart, Double> cartBid : cartBids.entrySet()) {
        if (!highest.containsKey(cartBid.getKey())) {
          highest.put(cartBid.getKey(), cartBid.getValue());
        } else if (!secondHighest.containsKey(cartBid.getKey())) {
          if (cartBid.getValue() <= highest.get(cartBid.getKey())) {
            secondHighest.put(cartBid.getKey(), cartBid.getValue());
          } else {
            secondHighest.put(cartBid.getKey(), highest.get(cartBid.getKey())); 
            highest.put(cartBid.getKey(), cartBid.getValue()); 
          }
        } else {
          if (highest.get(cartBid.getKey()) < cartBid.getValue()) {
            secondHighest.put(cartBid.getKey(), highest.get(cartBid.getKey()));
            highest.put(cartBid.getKey(), cartBid.getValue());
          } else if (secondHighest.get(cartBid.getKey()) < cartBid.getValue()) {
            secondHighest.put(cartBid.getKey(), cartBid.getValue());
          }
        }
      }
    }

    List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();

    // of everyone who bid on it, get the second highest price.
    for (Map.Entry<Integer, List<ICart>> anEntry : allocation.entrySet()) {
      for (ICart aCart : anEntry.getValue()) {
        if (secondHighest.containsKey(aCart)) {
          accountUpdates.add(new AccountUpdate(anEntry.getKey(),
              secondHighest.get(aCart), aCart));
        } else if (highest.containsKey(aCart)) {
          accountUpdates.add(
              new AccountUpdate(anEntry.getKey(), highest.get(aCart), aCart));
        } else {
          ErrorLogging
              .log("ERROR: Encountered unknown bid in payment rule: " + aCart);
        }
      }
    }
    
    System.out.println("PAYMENTS: " + accountUpdates);
    state.setPayments(accountUpdates);
  }
}
