package brown.user.agent.library;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.communication.messages.ITradeMessage;
import brown.user.agent.IAgent;

public class Tier1SpectrumAuctionAgent extends AbsSpectrumAuctionAgent implements IAgent {
	public Tier1SpectrumAuctionAgent(String name) {
		super(name);
	}

	@Override
	protected void onAuctionStart() {
	}
	
	@Override
	protected void onAuctionEnd(Map<Integer, Set<String>> allocations, Map<Integer, Double> payments,
			List<List<ITradeMessage>> tradeHistory) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Map<String, Double> getNationalBids(Map<String, Double> minBids) {
		Map<String, Double> bids = new HashMap<>();
		Map<String, Double> vals = new HashMap<>();
		for (String good : minBids.keySet()) {
			vals.put(good, this.getValuation(good));
		}
		
		for (String good : minBids.keySet()) {
			if (vals.get(good) >= minBids.get(good)) {
				bids.put(good, minBids.get(good));
			}
		}
		
		return bids;
	}
	
	@Override
	protected Map<String, Double> getRegionalBids(Map<String, Double> minBids) {
		Map<String, Double> bids = new HashMap<>();
		Map<String, Double> vals = new HashMap<>();
		for (String good : minBids.keySet()) {
			vals.put(good, this.getValuation(good));
		}
		
		for (String good : minBids.keySet()) {
			if (vals.get(good) >= minBids.get(good)) {
				bids.put(good, minBids.get(good));
			}
		}
		
		List<String> bestGoods = new ArrayList<>(bids.keySet());
		bestGoods.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return Double.compare(vals.get(o1), vals.get(o2));
			}
		});
		
		while (bestGoods.size() > 4) {
			bids.remove(bestGoods.remove(0));
		}
		
		return bids;
	}

}
