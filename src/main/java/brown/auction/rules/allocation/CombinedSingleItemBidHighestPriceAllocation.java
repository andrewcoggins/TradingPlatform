package brown.auction.rules.allocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

/**
 * allocation rule that allocates the item(s) to the highest bidder.
 * 
 * @author andrewcoggins
 *
 */
public class CombinedSingleItemBidHighestPriceAllocation extends AbsRule
implements IAllocationRule {

	@Override 
	public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
		Map<Integer, List<ICart>> highest = new HashMap<>();


		Map<IItem, Double> itemHighest = new HashMap<>();
		Map<IItem, Integer> itemWinner = new HashMap<>();


		for (ITradeMessage message : messages) {
			IBidBundle bundle = message.getBid();
			Map<ICart, Double> bids = bundle.getBids();
			for (ICart cartBid : bids.keySet()) {
				assert cartBid.getItems().size() == 1;
				IItem item = cartBid.getItems().get(0);
				itemHighest.putIfAbsent(item, 0.0);
				
				if (bids.get(cartBid) > itemHighest.get(item)) {
					itemHighest.put(item, bids.get(cartBid));
					itemWinner.put(item, message.getAgentID());
				}
			}
		}
		
		for (IItem item : itemWinner.keySet()) {
			if (!highest.containsKey(itemWinner.get(item))) {
				ICart c = new Cart();
				List<ICart> lst = new ArrayList<>(1);
				lst.add(c);
				highest.put(itemWinner.get(item), lst);
			}
			
			highest.get(itemWinner.get(item)).get(0).addToCart(item);
		}
		
		System.out.println("ALLOCATING: " + highest);
		state.setAllocation(highest);
	};
}
