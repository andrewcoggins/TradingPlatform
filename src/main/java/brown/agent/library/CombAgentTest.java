package brown.agent.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.agent.AbsCombinatorialProjectAgent;
import brown.exceptions.AgentCreationException;

public class CombAgentTest extends AbsCombinatorialProjectAgent {
	
	int round = 0;
	boolean print;

	public CombAgentTest(String host, int port, String name, boolean print) throws AgentCreationException {
		super(host, port, name);
		this.print = print;
	}

	@Override
	public List<Set<Integer>> onQueryRound() {
		List<Set<Integer>> queries = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Set<Integer> query = new HashSet<>();
			for (int j = 0; j < 100; j++) {
				query.add(j);
			}
			
			queries.add(query);
		}
		
		return queries;
	}

	@Override
	public void onQueryResults(Map<Set<Integer>, Double> results) {
		if (print) {
			for (Entry<Set<Integer>, Double> entry : results.entrySet()) {
				System.out.print("[");
				for (Integer i : entry.getKey()) {
					System.out.print(i + " ");
				}
				System.out.print("]");
				System.out.println(" " + entry.getValue());
			}
		}
	}

	@Override
	public Set<Integer> onBidRound() {
		Set<Integer> bid = new HashSet<>();
		
		if (round < 10) {
			bid.add(0);
		}
		
		round++;
		
		return bid;
	}

	@Override
	public void onBidResults(double[] allocations) {
		if (print) {
			for (int i = 0; i < 100; i++) {
				if (allocations[i] > 0) {
					System.out.print("(" + i + " " + allocations[i] + ") ");
				}
			}
			
			System.out.println("");
		}
	}

	@Override
	public void onAuctionStart() {
	}

	@Override
	public void onAuctionEnd(Set<Integer> finalBundle) {
		if (print) {
			for (int i = 0; i < 100; i++) {
				System.out.print("(" + i + ", " + getPrices()[i] + ") ");
			}
			
			System.out.println("");
			
			
			for (Integer i : finalBundle) {
				System.out.print(i + " ");
			}
		}
	}
	
	public static void main(String[] args) throws AgentCreationException {
		new CombAgentTest("localhost", 2121, "agent1", true);
		new CombAgentTest("localhost", 2121, "agent2", false);
		new CombAgentTest("localhost", 2121, "agent3", false);
		new CombAgentTest("localhost", 2121, "agent4", false);
		new CombAgentTest("localhost", 2121, "agent5", false);
		new CombAgentTest("localhost", 2121, "agent6", false);
		new CombAgentTest("localhost", 2121, "agent7", false);
		new CombAgentTest("localhost", 2121, "agent8", false);
		new CombAgentTest("localhost", 2121, "agent9", false);
		new CombAgentTest("localhost", 2121, "agent10", false);
		new CombAgentTest("localhost", 2121, "agent11", false);
		
		while (true) {}
	}

}
