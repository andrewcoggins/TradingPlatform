package brown.platform.information.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.platform.information.IWhiteboard;

//TODO: still need to accomodate different agents having different info. 

public class Whiteboard implements IWhiteboard {

  // map from market IDs to a list of market public states, for the timesteps. 
  // TODO: make the market state something that remembers. 
	private Map<Integer, IMarketState> innerMarketWhiteboard;
	private Map<Integer, IMarketState> outerMarketWhiteboard; 
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, IMarketState>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID, Integer agentID, 
      IMarketState marketPublicState) {
    IMarketState innerMarketStates; 
    if (this.innerMarketWhiteboard.containsKey(marketID)) {
      innerMarketStates = this.innerMarketWhiteboard.get(marketID); 
    } else {
      innerMarketStates = new MarketPublicState(); 
    }
    innerMarketStates = marketPublicState; 
    this.innerMarketWhiteboard.put(marketID, innerMarketStates); 
  }

  @Override
  public void postOuterInformation(Integer marketID,
      IMarketState marketPublicState) {
    this.outerMarketWhiteboard.put(marketID, marketPublicState); 
  }

  @Override
  public IMarketState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep) {
    // TODO: fix and uncomment
    //return this.innerMarketWhiteboard.get(marketID).get(timeStep); 
    return null; 
  }

  @Override
  public IMarketState getOuterInformation(Integer marketID) {
    return this.outerMarketWhiteboard.get(marketID); 
  }

  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
  }

}
