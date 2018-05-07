package brown.agent.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgentV2;
import brown.exceptions.AgentCreationException;

public class T2CombAgent extends AbsCombinatorialProjectAgentV2 {
	
	private static long initialLag = 19000;
	
	private Map<Set<Integer>, Double> valuation = new HashMap<>();

	public T2CombAgent(String host, int port, String name) throws AgentCreationException {
		super(host, port, name);
	}

	@Override
	public Set<Integer> onBidRound() {
    double maxValuePerItem = 0;
    Set<Integer> maxBundle = new HashSet<>();
    for (Entry<Set<Integer>, Double> entry : valuation.entrySet()) {
      double numItems = (double) entry.getKey().size();
      if ((entry.getValue() - this.getBundlePrice(entry.getKey())) / numItems > maxValuePerItem) {
        maxValuePerItem = (entry.getValue() - this.getBundlePrice(entry.getKey())) / numItems;
        maxBundle = entry.getKey();
      }
    }
    double bundleValue = queryValue(maxBundle);       
    if (getBundlePrice(maxBundle) < bundleValue) {
      return maxBundle;
    } else {
      return new HashSet<>();
    }	}

	@Override
	public void onBidResults(double[] allocations) {
	}

	@Override
	public void onAuctionStart() {		
		// query for random bundles for a second less than the initial lag
		long initTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - initTime < initialLag) {
			valuation.putAll(queryXORs(10, 20, 4));		
		}
	}

	@Override
	public void onAuctionEnd(Set<Integer> finalBundle) {
		// reset local variables
		valuation.clear();
	}
	
	public static void main(String[] args) throws AgentCreationException {
		new T1CombAgent("localhost", 2121, "agent1");
		new T1CombAgent("localhost", 2121, "agent2");
		new T1CombAgent("localhost", 2121, "agent3");
		new T1CombAgent("localhost", 2121, "agent4");
		new T1CombAgent("localhost", 2121, "agent5");
		new T1CombAgent("localhost", 2121, "agent6");
		new T1CombAgent("localhost", 2121, "agent7");
		new T2CombAgent("localhost", 2121, "T2 agent");
		
		while (true) {}
	}

}
