package brown.user.agent.library; // TODO: change this to your package

import brown.communication.channel.library.TwoSidedChannel;

public class PredictionMarketAgentTemplate extends AbsPredictionMarketAgent {

	public PredictionMarketAgentTemplate(String host, int port, String name)  {
		super(host, port, name);
	}

	@Override
	public void onMarketStart() {
		// TODO anything you want to compute before trading begins
	}

	@Override
	public void onMarketRequest(TwoSidedChannel channel) {
		// TODO decide if you want to bid/offer or not
	}

	@Override
	public void onTransaction(int quantity, double price) {
		// TODO anything your bot should do after a trade it's involved
		// in is completed
	}

	@Override
	public double getHighestBuy() {
		// TODO upper bound you would buy at
		return 0;
	}

	@Override
	public double getLowestSell() {
		// TODO lower bound they
		return 0;
	}

	public static void main(String[] args)  {
		new PredictionMarketAgentTemplate("localhost", 2121, "bot name goes here"); // TODO: name your bot
		while (true) {
		}
	}
}
