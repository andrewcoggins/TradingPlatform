package brown.agent.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.exceptions.AgentCreationException;

public class T2CombAgent extends AbsCombinatorialProjectAgentV2 {

	private static long initialLag = 20000;
	private static long lag = 1000;

	private List<Set<Integer>> valuation = new ArrayList<>();
	private Set<Integer> curGoods = new HashSet<>();
	
	private double prevValuePerItem = 0.0;
	private Set<Integer> prevBid = new HashSet<>();
	
	public T2CombAgent(String host, int port, String name) throws AgentCreationException {
		super(host, port, name);
	}

	@Override
	public Set<Integer> onBidRound() {
		long start = System.currentTimeMillis();
		Random r = new Random();
		while (System.currentTimeMillis() - start < 2 * lag - 250) {
			Set<Integer> bundle = new HashSet<>();
			bundle.addAll(valuation.get(r.nextInt(valuation.size() - 1)));
			bundle.addAll(curGoods);
			
			double numItems = (double) bundle.size();
			double valuePerItem = (queryValue(bundle) - getBundlePrice(bundle)) / numItems;
			if (valuePerItem > prevValuePerItem) {
				prevValuePerItem = valuePerItem;
				prevBid = bundle;
			}			
		}
		
		if (getBundlePrice(prevBid) < queryValue(prevBid)) {
			return prevBid;
		} else {
			return new HashSet<>();
		}
	}

	@Override
	public void onBidResults(double[] allocations) {
		curGoods.clear();
		for (int i = 0; i < allocations.length; i++) {
			if (allocations[i] == 1) {
				curGoods.add(i);
			}
		}
	}

	@Override
	public void onAuctionStart() {
		// query for random bundles for a second less than the initial lag
		long initTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - initTime < initialLag - 500) {
			valuation.addAll(queryXORs(10, 20, 4).keySet());
		}
	}

	@Override
	public void onAuctionEnd(Set<Integer> finalBundle) {
		// reset local variables
		valuation.clear();
		curGoods.clear();
		prevValuePerItem = 0;
		prevBid.clear();
	}

	public static void main(String[] args) throws AgentCreationException {
		new T2CombAgent("localhost", 2121, "agent1");
		new T2CombAgent("localhost", 2121, "agent2");
		new T2CombAgent("localhost", 2121, "agent3");
		new T2CombAgent("localhost", 2121, "agent4");
		new T2CombAgent("localhost", 2121, "agent5");
		new T2CombAgent("localhost", 2121, "agent6");
		new T2CombAgent("localhost", 2121, "agent7");
		new T2CombAgent("localhost", 2121, "agent8");

		while (true) {}
	}

}
