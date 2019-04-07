package brown.auction.rules.allocation.onesided;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;

/**
 * allocation rule that allocates the item(s) to the highest bidder. 
 * @author andrewcoggins
 *
 */
public class SimpleHighestPriceAllocation extends AbsRule implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
    for(ITradeMessage message : messages) {
      IBidBundle bundle = (IBidBundle) message.getBid(); 
      Map<ICart, Double> bids = bundle.getBids(); 
      
    }
  };

}
