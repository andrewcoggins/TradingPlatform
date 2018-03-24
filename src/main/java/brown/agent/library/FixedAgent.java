package brown.agent.library;

import brown.agent.AbsPredictionMarketAgent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;

// Calculates fair value and never updates
public class FixedAgent extends AbsPredictionMarketAgent {

	private double fair_value = 0;
	private double spread_epsilon = 5;
	private int exposure = 0; // Net number of contracts owned
	private int risklimit = 5;
	
	private String name;
	
	public FixedAgent(String host, int port, String name) throws AgentCreationException {
		super(host, port, name);
		
		this.name = name;
	}

	public FixedAgent(String host, int port, String name, double spread) throws AgentCreationException {
		super(host, port, name);
		spread_epsilon = spread;
		
		this.name = name;
	}

	@Override
	public void onMarketStart() {
		int decoys = this.getNumDecoys();
		exposure = 0;

		if (this.getCoin()) {
			fair_value = (double) (2 + decoys) / (double) (2 * decoys + 2) * 100;
		} else {
			fair_value = (double) (decoys) / (double) (2 * decoys + 2) * 100;
		}
		
		System.out.println(name + ": " + decoys + " decoys, " + this.getCoin());
	}

	@Override
	public void onMarketRequest(CallMarketChannel channel) {
		if (exposure >= risklimit) {
			this.cancel(1, true, channel);
		} else {
			this.buy(Math.floor(fair_value - spread_epsilon), 1, channel);
		}

		if (exposure <= -1 * risklimit) {
			this.cancel(100, false, channel);
		} else {
			this.sell(Math.ceil(fair_value + spread_epsilon), 1, channel);
		}

	}

	@Override
	public void onTransaction(int quantity, double price) {
		if (price > 0) { // contracts sold
			exposure -= quantity;
		} else {
			exposure += quantity;
		}
		
		if (price > 0) {
			System.out.println(name + ": sold " + quantity + " for " + price + ", exposure: " + exposure);
		} else {
			System.out.println(name + ": bought " + quantity + " for " + price + ", exposure: " + exposure);
		}
	}
	
	@Override
	public double getHighestBuy() {
		return fair_value - spread_epsilon;
	}

	@Override
	public double getLowestSell() {
		return fair_value + spread_epsilon;
	}

	public static void main(String[] args) throws AgentCreationException {
		new FixedAgent("localhost", 2121, "fixed1");
		new FixedAgent("localhost", 2121, "fixed2");
		new FixedAgent("localhost", 2121, "fixed3");
		new FixedAgent("localhost", 2121, "fixed4");
		new FixedAgent("localhost", 2121, "fixed5");
		
		while (true) {}
	}
}
