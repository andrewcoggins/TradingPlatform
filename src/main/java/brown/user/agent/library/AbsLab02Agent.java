package brown.user.agent.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.value.valuation.IValuation;
import brown.communication.bid.library.BidType;
import brown.communication.bidbundle.library.OneSidedBidBundle;
import brown.communication.channel.library.OneSidedChannel;
import brown.communication.messages.library.BankUpdateMessage;
import brown.communication.messages.library.GameReportMessage;
import brown.communication.messages.library.PrivateInformationMessage;
import brown.logging.library.Logging;
import brown.platform.tradeable.ITradeable;
import brown.system.setup.ISetup;

/**
 * Abstract agent for lab 2, first and second price simple sealed auction.
 * @author acoggins
 *
 */
public abstract class AbsLab02Agent extends AbsAuctionAgent {
	private double valuation;
	private ITradeable tradeable;

	public AbsLab02Agent(String host, int port, ISetup gameSetup) {
		super(host, port, gameSetup);
	}
	
	 public AbsLab02Agent(String host, int port, ISetup gameSetup, String name) {
	    super(host, port, gameSetup,name);
	  }
	
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {  
		super.onPrivateInformation(privateInfo);
		
		if (super.tradeables.size() != 1) {
			Logging.log("[x] AbsLab02Agent: multiple tradeables received; only storing first.");
		}
		
		this.tradeable = super.tradeables.get(0);
		this.valuation = super.valuation.getValuation(this.tradeable);
	}
	

	public void submitBid(OneSidedChannel channel, double bid) {
		Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>();
	    bidMap.put(tradeable, new BidType(bid, 1));
	    
	    // just bid valuation 
	    System.out.println("BID: " + bid);
	    channel.bid(this, new OneSidedBidBundle(bidMap));
	}
	
	@Override
	public void onBankUpdate(BankUpdateMessage bankUpdate) {
		Logging.log("BANKUPDATE: Agent: " + this.ID + ", " + bankUpdate.toString());
	}

	@Override
	public void onGameReport(GameReportMessage gameReport) {
		Logging.log("Game report received");
	} 
	
	public double getValuation() {
		return this.valuation;
	}
	
	public double sampleDistribution() {
		IValuation newValuationDist = super.vDistribution.sample();
		return newValuationDist.getValuation(this.tradeable);
	}
}
