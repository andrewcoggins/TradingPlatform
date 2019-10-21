package brown.platform.information.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.platform.information.IWhiteboard;

//TODO: still need to accomodate different agents having different info. 

public class Whiteboard implements IWhiteboard {

	private Map<Integer, List<IMarketPublicState>> innerMarketWhiteboard;
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, List<IMarketPublicState>>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID, Integer agentID, 
      IMarketPublicState marketPublicState) {
    List<IMarketPublicState> innerMarketStates; 
    if (this.innerMarketWhiteboard.containsKey(marketID)) {
      innerMarketStates = this.innerMarketWhiteboard.get(marketID); 
    } else {
      innerMarketStates = new LinkedList<IMarketPublicState>(); 
    }
    innerMarketStates.add(marketPublicState); 
    this.innerMarketWhiteboard.put(marketID, innerMarketStates); 
  }

  @Override
  public void postOuterInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.outerMarketWhiteboard.put(marketID, marketPublicState); 
  }

  @Override
  public IMarketPublicState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep) {
    return this.innerMarketWhiteboard.get(marketID).get(timeStep); 
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
