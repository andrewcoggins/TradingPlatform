package brown.platform.whiteboard.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.platform.whiteboard.IWhiteboard;

public class Whiteboard implements IWhiteboard {

	private Map<Integer, IMarketPublicState> openMarkets; 
	
	private Map<Integer, IMarketPublicState> previousMarkets; 
	
	public Whiteboard() {
		this.openMarkets = new HashMap<Integer, IMarketPublicState>(); 
		this.previousMarkets = new HashMap<Integer, IMarketPublicState>(); 
	}
	
	public IMarketPublicState getCurrentState(Integer openMarket) {
		return this.openMarkets.get(openMarket); 
	}
	
	public IMarketPublicState getPreviousState(Integer previousMarket) {
		return this.previousMarkets.get(previousMarket); 
	}
	
	// maybe more? 

}
