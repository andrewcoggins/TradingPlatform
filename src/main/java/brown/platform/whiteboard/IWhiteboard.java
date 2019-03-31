package brown.platform.whiteboard;

import brown.auction.marketstate.IMarketPublicState;

public interface IWhiteboard {
	
  
  public void postInnerInformation(Integer marketID, IMarketPublicState marketPublicState); 
  
  public void postOuterInformation(Integer marketID, IMarketPublicState marketPublicState); 
  
  public IMarketPublicState getInnerInformation(Integer marketID, Integer timeStep); 
  
  public IMarketPublicState getOuterInformation(Integer marketID); 
  
  public void clear(); 
  
}
