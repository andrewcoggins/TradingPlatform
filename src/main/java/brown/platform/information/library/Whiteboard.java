package brown.platform.information.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.platform.information.IWhiteboard;
 

// whiteboard has to to with IR, meaning sometimes 
public class Whiteboard implements IWhiteboard {

  // map from market IDs to market public states 
  
  // for in-progress markets: inner markets (governed by Inner IR)
	private Map<Integer, IMarketPublicState> innerMarketWhiteboard;
	// for finished markets: outer markets (governed by outer IR)
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	
	// for simulation reports. map from market ID to unredacted public state
	private Map<Integer, IMarketPublicState> simulationReportWhiteboard; 
	
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.simulationReportWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
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
  public void postSimulationInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.simulationReportWhiteboard.put(marketID, marketPublicState);
    
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
  public IMarketPublicState getSimulationInformation(Integer marketID) {
    return this.simulationReportWhiteboard.get(marketID); 
  }
  
  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
    this.simulationReportWhiteboard.clear(); 
  }
  

}
