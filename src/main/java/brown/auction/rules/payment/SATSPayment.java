package brown.auction.rules.payment;

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
import brown.auction.rules.IPaymentRule;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.SATSTradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;
import brown.platform.item.ICart;
import brown.platform.item.library.Cart;
import brown.platform.item.library.SATSItem;

public class SATSPayment implements IPaymentRule {

	@Override
	public void setOrders(IMarketState state, List<ITradeMessage> tradeMessages) {
		Map<Bidder<Good>, Integer> bidderToAgentID = new HashMap<>();

		double bestRev = 0;
		Payment<Good> bestPayment = null;
		List<List<ITradeMessage>> tradeHistory = state.getTradeHistory();
		for (List<ITradeMessage> round : tradeHistory) {
			List<XORBid<Good>> bids = new LinkedList<>();
			for (ITradeMessage message : round) {
				XORBid<Good> xor = ((SATSTradeMessage) message).getXOR();
				bids.add(xor);
				bidderToAgentID.put(xor.getBidder(), message.getAgentID());
			}
			XORWinnerDetermination<Good> wd = new XORWinnerDetermination<Good>(bids);
			Payment<Good> payment = new VCGMechanism<Good>(wd).getPayment();
			double rev = payment.getTotalPayments();
			if (rev > bestRev) {
				bestRev = rev;
				bestPayment = null;
			}
		}

		if (bestPayment == null) {
			return;
		}

		Map<Integer, Double> payment = new HashMap<>();
		for (Bidder<Good> winner : bestPayment.getWinners()) {
			int agentID = bidderToAgentID.get(winner);
			payment.putIfAbsent(agentID, 0.0);
			payment.put(agentID, payment.get(agentID) + bestPayment.paymentOf(winner).getAmount());
		}
		
		List<IAccountUpdate> updates = new LinkedList<>();
		for (Map.Entry<Integer, Double> ent : payment.entrySet()) {
			List<ICart> carts = state.getAllocation().get(ent.getKey());
			ICart cart = new Cart();
			if (carts != null && carts.isEmpty()) {
				cart = carts.get(0);
			}
			updates.add(new AccountUpdate(ent.getKey(), ent.getValue(), cart));
		}
		state.setPayments(updates);
	}

}
