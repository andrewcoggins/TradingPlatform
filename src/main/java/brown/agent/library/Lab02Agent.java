package brown.agent.library;

import brown.agent.AbsLab02Agent;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.library.SSSPSetup;

public class Lab02Agent extends AbsLab02Agent {

	public Lab02Agent(String host, int port) throws AgentCreationException {
		super(host, port, new SSSPSetup());
	}

	@Override
	public void onAuction(AuctionChannel channel) {
		// TODO: decide how to bid
		
		// this just bids your valuation
		this.submitBid(channel, 0.5*this.getValuation());
	}
	
	public static void main(String[] args) throws AgentCreationException {
		new Lab02Agent("mslab4b-l", 2121);
		
		while (true) {}
	}
}
