package brown.platform.information.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.platform.information.IWhiteboard;
 

// whiteboard has to to with IR, meaning sometimes 
public class Whiteboard implements IWhiteboard {

  // map from market IDs to market public states 
  
  // for in-progress markets: inner markets (governed by Inner IR)
  // map from market ID to map from agent ID to a market public state for each timestep. 
	private Map<Integer, Map<Integer, List<IMarketPublicState>>> innerMarketWhiteboard;
	// for finished markets: outer markets (governed by outer IR)
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	
	// for simulation reports. map from market ID to unredacted public state
	private Map<Integer, IMarketPublicState> simulationReportWhiteboard; 
	
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, Map<Integer, List<IMarketPublicState>>>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.simulationReportWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID, Integer agentID, 
      IMarketPublicState marketPublicState) {
    Map<Integer, List<IMarketPublicState>> innerMarketPublicStates;  
    if (this.innerMarketWhiteboard.containsKey(marketID)) {
      innerMarketPublicStates = this.innerMarketWhiteboard.get(marketID); 
    } else {
      innerMarketPublicStates = new HashMap<Integer, List<IMarketPublicState>>(); 
    }
    List<IMarketPublicState> agentPublicStates; 
    if (innerMarketPublicStates.containsKey(agentID)) {
      agentPublicStates = innerMarketPublicStates.get(agentID); 
    } else {
      agentPublicStates = new LinkedList<IMarketPublicState>(); 
    }
    agentPublicStates.add(marketPublicState); 
    innerMarketPublicStates.put(agentID, agentPublicStates); 
    
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
    return this.innerMarketWhiteboard.get(marketID).get(agentID).get(timeStep); 
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
  public Map<Integer, IMarketPublicState> getSimulationReportWhiteboard() {
    return this.simulationReportWhiteboard; 
  } 
  
  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
    this.simulationReportWhiteboard.clear(); 
  }
  

}
