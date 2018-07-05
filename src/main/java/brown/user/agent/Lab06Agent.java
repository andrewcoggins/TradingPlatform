package brown.user.agent; // TODO: change this to your package

import brown.mechanism.channel.CallMarketChannel;

/**
 * template agent for lab06- prediction markets for cs1951k
 * @author kerry
 *
 */
public class Lab06Agent extends AbsLab06Agent {

	public Lab06Agent(String host, int port, String name)  {
		super(host, port, name);
	}

	@Override
	public void onMarketStart() {
		// TODO anything you want to compute before trading begins
	}

	@Override
	public void onMarketRequest(CallMarketChannel channel) {
		// TODO decide if you want to bid/offer or not
	}

	@Override
	public void onTransaction(int quantity, double price) {
		// TODO anything your bot should do after a trade it's involved
		// in is completed
	}
	
	public static void main(String[] args)  {
		new Lab06Agent("mslab4b-l", 2121, "bot name goes here"); // TODO: name your bot
		while(true){}      
	}

}
