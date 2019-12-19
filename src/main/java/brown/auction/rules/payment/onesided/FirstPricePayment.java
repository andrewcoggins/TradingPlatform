package brown.auction.rules.payment.onesided;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.logging.library.ErrorLogging;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;

public class FirstPricePayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state, List<ITradeMessage> messages) {
    // TODO Auto-generated method stub
    Map<Integer, List<ICart>> allocation = state.getAllocation();

    Map<ICart, Double> highest = new HashMap<ICart, Double>();

    for (ITradeMessage tradeMessage : messages) {
      IBidBundle bundle = (IBidBundle) tradeMessage.getBid();
      Map<ICart, Double> cartBids = bundle.getBids();
      for (Map.Entry<ICart, Double> cartBid : cartBids.entrySet()) {
        if (!highest.containsKey(cartBid.getKey())) {
          highest.put(cartBid.getKey(), cartBid.getValue());
        } else {
          if (highest.get(cartBid.getKey()) < cartBid.getValue()) {
            highest.put(cartBid.getKey(), cartBid.getValue());
          }
        }
      }
    }

    List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();

    // of everyone who bid on it, get the highest price.
    for (Map.Entry<Integer, List<ICart>> anEntry : allocation.entrySet()) {
      for (ICart aCart : anEntry.getValue()) {
         if (highest.containsKey(aCart)) {
          accountUpdates.add(
              new AccountUpdate(anEntry.getKey(), highest.get(aCart), aCart));
        } else {
          ErrorLogging
              .log("ERROR: Encountered unknown bid in payment rule: " + aCart);
        }
      }
    }
    
    state.setPayments(accountUpdates);
  }

}
