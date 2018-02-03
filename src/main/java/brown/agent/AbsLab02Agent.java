package brown.agent;

import java.util.HashMap;
import java.util.Map;

import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.value.valuation.IValuation;

public abstract class AbsLab02Agent extends AbsSimpleSealedAgent {
	private double valuation;
	private ITradeable tradeable;

	public AbsLab02Agent(String host, int port, ISetup gameSetup) throws AgentCreationException {
		super(host, port, gameSetup);
	}
	
	public abstract void onAuction(AuctionChannel channel);
	
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {  
		super.onPrivateInformation(privateInfo);
		
		if (super.tradeables.size() != 1) {
			Logging.log("[x] AbsLab02Agent: multiple tradeables received; only storing first.");
		}
		
		this.tradeable = super.tradeables.get(0);
		this.valuation = super.valuation.getValuation(this.tradeable);
	}
	
	@Override
	public void onSSSP(AuctionChannel channel) {
		this.onAuction(channel);
	}

	public void submitBid(AuctionChannel channel, double bid) {
		Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>();
	    bidMap.put(tradeable, new BidType(bid, 1));
	    
	    // just bid valuation 
	    System.out.println("BID: " + bid);
	    channel.bid(this, new AuctionBidBundle(bidMap));
	}
	
	public double getValuation() {
		return this.valuation;
	}
	
	public double sampleDistribution() {
		IValuation newValuationDist = super.vDistribution.sample();
		return newValuationDist.getValuation(this.tradeable);
	}
}
