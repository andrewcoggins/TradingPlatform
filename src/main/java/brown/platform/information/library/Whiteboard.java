package brown.platform.information.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.platform.information.IWhiteboard;

//TODO: still need to accomodate different agents having different info. 

public class Whiteboard implements IWhiteboard {

  // map from market IDs to a list of market public states, for the timesteps. 
  // TODO: make the market state something that remembers. 
	private Map<Integer, IMarketPublicState> innerMarketWhiteboard;
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID, Integer agentID, 
      IMarketPublicState marketPublicState) {
    IMarketPublicState innerMarketPublicStates; 
    if (this.innerMarketWhiteboard.containsKey(marketID)) {
      innerMarketPublicStates = this.innerMarketWhiteboard.get(marketID); 
    } else {
      innerMarketPublicStates = new MarketPublicState(); 
    }
    innerMarketPublicStates = marketPublicState; 
    this.innerMarketWhiteboard.put(marketID, innerMarketPublicStates); 
  }

  @Override
  public void postOuterInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.outerMarketWhiteboard.put(marketID, marketPublicState); 
  }

  @Override
  public IMarketPublicState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep) {
    // TODO: fix and uncomment
    //return this.innerMarketWhiteboard.get(marketID).get(timeStep); 
    return null; 
  }

  @Override
  public IMarketPublicState getOuterInformation(Integer marketID) {
    return this.outerMarketWhiteboard.get(marketID); 
  }

  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
  }

}
