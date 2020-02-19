package brown.auction.rules.payment;

import java.util.ArrayList;
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
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class CombinedSingleItemBidSecondPricePayment extends AbsRule implements IPaymentRule {

	@Override
	public void setOrders(IMarketState state, List<ITradeMessage> messages) {
		Map<Integer, List<ICart>> allocation = state.getAllocation();

		Map<Integer, List<ICart>> highest = new HashMap<>();

		Map<IItem, Double> itemHighest = new HashMap<>();
		Map<IItem, Double> itemSecondHighest = new HashMap<>();


		for (ITradeMessage message : messages) {
			IBidBundle bundle = message.getBid();
			Map<ICart, Double> bids = bundle.getBids();
			for (ICart cartBid : bids.keySet()) {
				assert cartBid.getItems().size() == 1;
				IItem item = cartBid.getItems().get(0);
				itemHighest.putIfAbsent(item, 0.0);
				itemSecondHighest.putIfAbsent(item, 0.0);

				if (bids.get(cartBid) > itemHighest.get(item)) {
					itemSecondHighest.put(item, itemHighest.get(item));
					itemHighest.put(item, bids.get(cartBid));
				} else if (bids.get(cartBid) > itemSecondHighest.get(item)) {
					itemSecondHighest.put(item, bids.get(cartBid));
				}
			}
		}

		List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();

		// of everyone who bid on it, get the second highest price.
		for (Map.Entry<Integer, List<ICart>> anEntry : allocation.entrySet()) {
			Integer agent = anEntry.getKey();
			assert anEntry.getValue().size() == 1;
			List<IItem> items = anEntry.getValue().get(0).getItems();
			for (IItem item : items) {
				ICart c = new Cart();
				c.addToCart(item);
				accountUpdates.add(new AccountUpdate(agent, itemSecondHighest.get(item), c));
			}
		}

		System.out.println("PAYMENTS: " + accountUpdates);
		state.setPayments(accountUpdates);
	}
}
