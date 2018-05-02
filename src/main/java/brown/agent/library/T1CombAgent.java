package brown.agent.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.exceptions.AgentCreationException;

public class T1CombAgent extends AbsCombinatorialProjectAgentV2 {
	
	private static long initialLag = 10000;
	
	private Set<Integer> bundle = new HashSet<>();
	private double bundleValue = 0;
	private Map<Set<Integer>, Double> valuation = new HashMap<>();

	public T1CombAgent(String host, int port, String name) throws AgentCreationException {
		super(host, port, name);
	}

	@Override
	public Set<Integer> onBidRound() {
		// bid for our bundle, if it's price isn't too high
		if (getBundlePrice(bundle) < bundleValue) {
			return bundle;
		} else {
			return new HashSet<>();
		}
	}

	@Override
	public void onBidResults(double[] allocations) {
		// do nothing
	}

	@Override
	public void onAuctionStart() {		
		// query for random bundles for a second less than the initial lag
		long initTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - initTime < initialLag) {
			valuation.putAll(queryXORs(10, 20, 4));
		}
		
		System.out.println("size: " + valuation.size());
		
		// find best bundle, relative to value per item
		double maxValuePerItem = 0;
		Set<Integer> maxBundle = new HashSet<>();
		for (Entry<Set<Integer>, Double> entry : valuation.entrySet()) {
			double numItems = (double) entry.getKey().size();
			if (entry.getValue() / numItems > maxValuePerItem) {
				maxValuePerItem = entry.getValue() / numItems;
				maxBundle = entry.getKey();
			}
		}
		
		bundle = maxBundle;
		bundleValue = queryValue(bundle);
	}

	@Override
	public void onAuctionEnd(Set<Integer> finalBundle) {
		// reset local variables
		valuation.clear();
		bundle.clear();
		bundleValue = 0;
	}
	
	public static void main(String[] args) throws AgentCreationException {
		new T1CombAgent("localhost", 2121, "agent1");
		new T1CombAgent("localhost", 2121, "agent2");
		new T1CombAgent("localhost", 2121, "agent3");
		new T1CombAgent("localhost", 2121, "agent4");
		new T1CombAgent("localhost", 2121, "agent5");
		new T1CombAgent("localhost", 2121, "agent6");
		new T1CombAgent("localhost", 2121, "agent7");
		new T1CombAgent("localhost", 2121, "agent8");
		
		while (true) {}
	}

}
