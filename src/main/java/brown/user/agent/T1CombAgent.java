package brown.user.agent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * final project cs1951k- Tier 1 bot
 * @author kerry
 *
 */
public class T1CombAgent extends AbsCombinatorialProjectAgentV2 {
	
	private static long initialLag = 20000;
	
	private Set<Integer> bundle = new HashSet<>();
	private double bundleValue = 0;
	private Map<Set<Integer>, Double> valuation = new HashMap<>();

	public T1CombAgent(String host, int port, String name)  {
		super(host, port, name);
	}

	@Override
	public Set<Integer> onBidRound() {
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
		while (System.currentTimeMillis() - initTime < initialLag - 7000) {
			valuation.putAll(queryXORs(10, 20, 4));		
		}
				
		// find best bundle, relative to value per item
		double maxValuePerItem = 0;
		Set<Integer> maxBundle = new HashSet<>();
		long start = System.currentTimeMillis();
		for (Entry<Set<Integer>, Double> entry : valuation.entrySet()) {
			if (System.currentTimeMillis() - start > 6500) {
				break;
			}
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
	
	public static void main(String[] args)  {
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
