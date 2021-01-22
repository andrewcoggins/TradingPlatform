package brown.simulations;

import java.util.LinkedList;
import java.util.List;

import brown.platform.utils.Utils;

public class BasicSimulation extends AbsUserSimulation {
	private List<String> agentName;
	
	public BasicSimulation(List<String> agentClass, List<String> agentName, String inputJSON,
			String outFile, boolean writeToFile) {
		super(agentClass, inputJSON, outFile, writeToFile);
		this.agentName = agentName;
	}
	
	public BasicSimulation(List<String> agentClass, String inputJSON, String outfile, boolean writeToFile) {
		this(agentClass, null, inputJSON, outfile, writeToFile);
	}

	public void run() throws InterruptedException {

		ServerRunnable sr = new ServerRunnable();

		List<AgentRunnable> ars = new LinkedList<>();
		List<Thread> agentThreads = new LinkedList<>();
		int i = 0;
		for (String s : agentClass) {
			String name = (this.agentName == null) ? ("agent_" + i) : this.agentName.get(i);
			AgentRunnable ar = new AgentRunnable(agentClass.get(i), name);
			ars.add(ar);
			agentThreads.add(new Thread(ar));
			i++;
		}



		Thread st = new Thread(sr);

		st.start();
		Utils.sleep(5000);

		if (agentClass != null) {
			for (Thread t : agentThreads) {
				t.start();
			}
		}

		while (true) {
			if (!st.isAlive()) {
				for (Thread t : agentThreads) {
					t.interrupt();
				}
				break;
			}
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		List<String> agentList = new LinkedList<String>();
		agentList.add("brown.user.agent.library.SimpleAgent");
		BasicSimulation basicSim = new BasicSimulation(agentList,
				"input_configs/second_price_auction.json", "outfile", false);
		basicSim.run();
	}

}

