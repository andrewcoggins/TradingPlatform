package brown.auction.rules.allocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.spectrumauctions.sats.core.bidlang.xor.XORBid;
import org.spectrumauctions.sats.core.model.Bidder;
import org.spectrumauctions.sats.core.model.Good;
import org.spectrumauctions.sats.mechanism.domain.Payment;
import org.spectrumauctions.sats.mechanism.vcg.VCGMechanism;
import org.spectrumauctions.sats.opt.domain.Allocation;
import org.spectrumauctions.sats.opt.xor.XORWinnerDetermination;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.SATSTradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.GSVM18Item;

public class SMRAAllocation extends AbsRule implements IAllocationRule {

	@Override
	public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
		/*
		Map<Bidder<Good>, Integer> bidderToAgentID = new HashMap<>();
		
		double bestRev = 0;
		Allocation<Good> bestAlloc = null;
		List<List<ITradeMessage>> tradeHistory = state.getTradeHistory();
		for (List<ITradeMessage> round : tradeHistory) {
			List<XORBid<Good>> bids = new LinkedList<>();
			for (ITradeMessage message : round) {
				XORBid<Good> xor = ((SATSTradeMessage)message).getXOR();
				bids.add(xor);
				bidderToAgentID.put(xor.getBidder(), message.getAgentID());
			}
			XORWinnerDetermination<Good> wd = new XORWinnerDetermination<Good>(bids);
			Allocation<Good> alloc = wd.calculateAllocation();
			Payment<Good> payment = new VCGMechanism<Good>(wd).getPayment();
			double rev = payment.getTotalPayments();
			if (rev > bestRev) {
				bestRev = rev;
				bestAlloc = alloc;
			}
		}
		
		if (bestAlloc == null) {
			return;
		}
		*/
		
		Map<Integer, List<ICart>> allocation = new HashMap<>();
		
		Map<Integer, List<ICart>> oldAllocation = state.getAllocation();
		Map<IItem, Integer> oldAllocByItem = new HashMap<>();
		for (Map.Entry<Integer, List<ICart>> ent : oldAllocation.entrySet()) {
			for (ICart cart : ent.getValue()) {
				for (IItem item : cart.getItems()) {
					oldAllocByItem.put(item, ent.getKey());
				}
			}
		}
		
		if (state.isOpen()) {
			// not last round; tentative
			Map<IItem, Integer> alloc = new HashMap<>();
			Map<IItem, Double> highest = new HashMap<>();
			Map<String, Double> reserve = state.getReserves();
			List<ITradeMessage> msgs = new ArrayList<>(messages);
			Collections.shuffle(msgs);
			for (ITradeMessage message : msgs) {
				int agent = message.getAgentID().intValue();
				for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
					ICart cart = ent.getKey();
					double bid = ent.getValue();
					
					assert cart.getItems().size() == 1;
					IItem item = cart.getItems().get(0);
					
					highest.putIfAbsent(item, reserve.get(item.getName()));
					
					double h1 = highest.get(item);
					
					if (bid >= h1) {
						highest.put(item, bid);
						alloc.put(item, agent);
					}
				}
			}
			
			Set<Integer> allocatedAgents = new HashSet<>(alloc.values());
			allocatedAgents.addAll(oldAllocation.keySet());
			for (Integer agent : allocatedAgents) {
				allocation.put(agent, Lists.newArrayList(new Cart()));
			}
			for (Map.Entry<IItem, Integer> ent : alloc.entrySet()) {
				allocation.get(ent.getValue()).get(0).addToCart(ent.getKey());
			}
			for (Map.Entry<IItem, Integer> ent : oldAllocByItem.entrySet()) {
				if (!alloc.containsKey(ent.getKey())) {
					allocation.get(ent.getValue()).get(0).addToCart(ent.getKey());
				}
			}
		} else {
			// auction over; search all rounds
			List<List<ITradeMessage>> history = state.getTradeHistory();
			double bestRev = 0.0;
			Map<Integer, List<ICart>> bestAlloc = null;
			for (int round = 0; round < history.size(); round++) {
				if (history.get(round).isEmpty()) {
					continue;
				}
				
				double rev = 0.0;
				Map<Integer, List<ICart>> alloc = new HashMap<>();
				for (ITradeMessage msg : history.get(round)) {
					if (msg.getAuctionID().intValue() == -2) {
						int agent = msg.getAgentID().intValue();
						alloc.putIfAbsent(agent, new ArrayList<>());
						for (Map.Entry<ICart, Double> ent : msg.getBid().getBids().entrySet()) {
							alloc.get(agent).add(ent.getKey());
							rev += ent.getValue();
						}
					}
				}
				if (rev >= bestRev) {
					bestRev = rev;
					bestAlloc = alloc;
				}
			}
			
			if (bestAlloc != null) {
				allocation = bestAlloc;
			}
		}
		
		state.setAllocation(allocation);
	}

}
