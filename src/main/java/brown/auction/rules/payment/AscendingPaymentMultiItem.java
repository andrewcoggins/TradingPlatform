package brown.auction.rules.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.communication.messages.ITradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class AscendingPaymentMultiItem implements IPaymentRule {

	@Override
	public void setOrders(IMarketState state, List<ITradeMessage> messages) {
		if (state.getAllocation().isEmpty()) {
			return;
		}

		// get the most recent history.
		List<List<ITradeMessage>> tradeHistory = state.getTradeHistory();

		Map<IItem, Double> prices = new HashMap<>();
		for (int round = 0; round < tradeHistory.size(); round++) {
			for (ITradeMessage message : tradeHistory.get(round)) {
				for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
					for (IItem item : ent.getKey().getItems()) {
						prices.put(item, ent.getValue());
					}
				}
			}
		}
		
		List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();
		for (Map.Entry<Integer, List<ICart>> ent : state.getAllocation().entrySet()) {
			for (ICart cart : ent.getValue()) {
				double price = 0;
				for (IItem item : cart.getItems()) {
					price += prices.get(item);
				}
				accountUpdates.add(new AccountUpdate(ent.getKey(), price, cart));
			}
			
		}
		
		state.setPayments(accountUpdates);

	}
}