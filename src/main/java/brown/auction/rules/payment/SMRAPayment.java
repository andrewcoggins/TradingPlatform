package brown.auction.rules.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class SMRAPayment implements IPaymentRule {

	@Override
	public void setOrders(IMarketState state, List<ITradeMessage> messages) {
		if (state.getAllocation().isEmpty()) {
			return;
		}
		
		List<List<ITradeMessage>> history = state.getTradeHistory();
		List<IAccountUpdate> accountUpdates = new LinkedList<IAccountUpdate>();
		
		if (state.isOpen()) {
			// not last round. second-price payments floored by reserve.
			Map<String, Double> reserves = state.getReserves();
			Map<String, Double> p1 = new HashMap<>();
			Map<String, Double> p2 = new HashMap<>();
			for (ITradeMessage message : messages) {
				for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
					ICart cart = ent.getKey();
					double bid = ent.getValue();
					
					assert cart.getItems().size() == 1;
					IItem item = cart.getItems().get(0);
					
					p1.putIfAbsent(item.getName(), 0.0);
					p2.putIfAbsent(item.getName(), 0.0);
					
					double h1 = p1.get(item.getName());
					double h2 = p2.get(item.getName());
					
					if (bid > h1) {
						p2.put(item.getName(), h1);
						p1.put(item.getName(), bid);
					} else if (bid > h2) {
						p2.put(item.getName(), bid);
					}
				}
			}
			
			Map<String, Double> itemPrices = new HashMap<>();
			for (Map.Entry<Integer, List<ICart>> ent : state.getAllocation().entrySet()) {
				for (ICart cart : ent.getValue()) {
					for (IItem item : cart.getItems()) {
						double price = 0.0;
						if (p1.containsKey(item.getName())) {
							price = Math.max(p2.get(item.getName()), reserves.get(item.getName()));
						} else {
							// there were no bids. price is the same as last round.
							for (IAccountUpdate upd : state.getPayments()) {
								if (upd.getCart().containsItem(item.getName())) {
									price = upd.getCost().doubleValue();
									break;
								}
							}
						}
						ICart c = new Cart();
						c.addToCart(item);
						accountUpdates.add(new AccountUpdate(ent.getKey(), price, c));
						itemPrices.put(item.getName(), price);
					}
				}
			}
			
			// add allocations and reserves to trade history as "bids"
			// this is the only way I can think of to keep persistent data on round-by-round allocations & reserves
			// we should add a real way to do this later 
			Map<ICart, Double> reserveBids = new HashMap<>();
			for (Map.Entry<String, Double> ent : reserves.entrySet()) {
				ICart cart = new Cart();
				cart.addToCart(new Item(ent.getKey()));
				reserveBids.put(cart, ent.getValue());
			}
			history.get(history.size() - 1).add(new TradeMessage(-1, -1, -1, new OneSidedBidBundle(reserveBids)));
			for (Map.Entry<Integer, List<ICart>> ent : state.getAllocation().entrySet()) {
				Map<ICart, Double> map = new HashMap<>();
				for (ICart cart : ent.getValue()) {
					double price = 0.0;
					for (IItem item : cart.getItems()) {
						price += itemPrices.get(item.getName());
					}
					map.put(cart, price);
				}
				history.get(history.size() - 1).add(new TradeMessage(-1, ent.getKey(), -2, new OneSidedBidBundle(map)));
			}
		} else {
			// auction over; search all rounds
			double bestRev = 0.0;
			Map<Integer, Double> bestPayment = null;
			for (int round = 0; round < history.size(); round++) {
				if (history.get(round).isEmpty()) {
					continue;
				}
				
				double rev = 0.0;
				Map<Integer, Double> payment = new HashMap<>();
				for (ITradeMessage msg : history.get(round)) {
					if (msg.getAuctionID().intValue() == -2) {
						int agent = msg.getAgentID().intValue();
						payment.putIfAbsent(agent, 0.0);
						for (Map.Entry<ICart, Double> ent : msg.getBid().getBids().entrySet()) {
							rev += ent.getValue();
							payment.put(agent, payment.get(agent) + ent.getValue());
						}
					}
				}
				System.out.println("ROUND: " + round + " REV: " + rev);
				if (rev >= bestRev) {
					bestRev = rev;
					bestPayment = payment;
				}
			}
			
			if (bestPayment != null) {
				for (Map.Entry<Integer, Double> ent : bestPayment.entrySet()) {
					accountUpdates.add(new AccountUpdate(ent.getKey(), ent.getValue(), state.getAllocation().get(ent.getKey()).get(0)));
				}
			}
			
			// remove hacky entries from trade history
			for (List<ITradeMessage> lst : history) {
				lst.removeIf(message -> message.getAuctionID().intValue() < 0);
			}
		}
		
		state.setPayments(accountUpdates);

	}
}