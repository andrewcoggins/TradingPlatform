package brown.auction.rules.activity;

import java.util.List;
import java.util.Map;

import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public abstract class AbsActivity extends AbsRule implements IActivityRule {
  
  
  protected boolean isWellFormed(ITradeMessage aBid, ICart items) { 
    IBidBundle bundle = (IBidBundle) aBid.getBid();
    Map<ICart, Double> bidMap = bundle.getBids(); 
    // one sided
    if (!(aBid.getBid().getType() == BidType.OneSidedBidBundle)) return false; 
    // have some messages.
    if (bidMap.keySet().isEmpty()) return false; 
    // no messages not being bid on in the market in the TradeMessage.
    for (ICart aCart : bidMap.keySet()) {
      List<IItem> cartItems = aCart.getItems(); 
      for (IItem anItem : cartItems) {
        if (!items.getItems().contains(anItem)) return false; 
      }
    }
    return true; 
  }
  
}
