package brown.platform.information;

import brown.auction.marketstate.IMarketPublicState;

public interface IWhiteboard {
  
  public void postInnerInformation(Integer marketID, Integer agentID, IMarketPublicState marketPublicState); 
  
  public void postOuterInformation(Integer marketID, IMarketPublicState marketPublicState); 

  public void postSimulationInformation(Integer marketID, IMarketPublicState marketPublicState); 
  
  public IMarketPublicState getInnerInformation(Integer marketID, Integer agentID, Integer timeStep); 
  
  public IMarketPublicState getOuterInformation(Integer marketID); 
  
  public IMarketPublicState getSimulationInformation(Integer marketID); 
  
  
  
  public void clear(); 
  
}
