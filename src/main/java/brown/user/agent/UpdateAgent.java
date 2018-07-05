package brown.user.agent;

import brown.mechanism.channel.CallMarketChannel;

/**
 * Calculates fair value and then updates in direction of trades it does
 * @author kerry
 *
 */
public class UpdateAgent extends AbsPredictionMarketAgent {

	private double fair_value = 0;
	private double update_epsilon = 1;
	private double spread_epsilon = 5;
	boolean updated = false;
	private int exposure = 0; // Net number of contracts owned
	private int risklimit = 5;

	public UpdateAgent(String host, int port, String name) {
		super(host, port, name);
	}

	@Override
	public void onMarketStart() {
	  this.exposure = 0;
		int decoys = this.getNumDecoys();

		if (this.getCoin()) {
			fair_value = (double) (2 + decoys) / (double) (2 * decoys + 2) * 100;
		} else {
			fair_value = (double) (decoys) / (double) (2 * decoys + 2) * 100;
		}

		System.out.println("DECOYS: " + decoys + ", FAIR VALUE: " + fair_value);

		switch (decoys) {
		case 1:
			update_epsilon = 0;
			spread_epsilon = 4;
			risklimit = 7;
			break;
		case 2:
			update_epsilon = 1;
			spread_epsilon = 6;
			risklimit = 6;
			break;
		case 3:
			update_epsilon = 2;
			spread_epsilon = 8;
			risklimit = 5;
			break;
		default:
			update_epsilon = 3;
			spread_epsilon = 8;
			risklimit = 4;
		}

	}

	@Override
	public void onMarketRequest(CallMarketChannel channel) {
		if (updated) {
			this.cancel(1, true, channel);
			this.cancel(100, false, channel);
			updated = false;
		}

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
		if (price > 0) {
			fair_value += update_epsilon;
			fair_value = Math.min(fair_value, 94);

			exposure -= quantity;
		} else {
			fair_value -= update_epsilon;
			fair_value = Math.max(fair_value, 6);

			exposure += quantity;
		}

		if (price > 0) {
			System.out.print("SOLD AT " + price);
		} else {
			System.out.print("BOUGHT AT " + price);
		}

		System.out.println(" NEW FAIR VALUE: " + fair_value);

		updated = true;
	}

	@Override
	public double getHighestBuy() {
		return fair_value - spread_epsilon;
	}

	@Override
	public double getLowestSell() {
		return fair_value + spread_epsilon;
	}

	public static void main(String[] args) {

		new UpdateAgent("localhost", 2121, "Update1");

		while (true) {
		}
	}
}
