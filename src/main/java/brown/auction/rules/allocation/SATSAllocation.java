package brown.auction.rules.allocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.SATSTradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.library.Cart;
import brown.platform.item.library.SATSItem;

public class SATSAllocation extends AbsRule implements IAllocationRule {

	@Override
	public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
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
		
		Map<Integer, List<ICart>> allocation = new HashMap<>();
		for (Bidder<Good> winner : bestAlloc.getWinners()) {
			int agentID = bidderToAgentID.get(winner);
			allocation.putIfAbsent(agentID, new LinkedList<>());
			ICart cart = new Cart();
			for (Good g : bestAlloc.getAllocation(winner)) {
				cart.addToCart(new SATSItem(g));
			}
			allocation.get(agentID).add(cart);
		}
		
		state.setAllocation(allocation);
	}

}
