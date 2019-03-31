package brown.platform.whiteboard.library;

import java.util.HashMap;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.platform.whiteboard.IWhiteboard;

public class Whiteboard implements IWhiteboard {

	private Map<Integer, IMarketPublicState> innerMarketWhiteboard;
	private Map<Integer, IMarketPublicState> outerMarketWhiteboard; 
	
	public Whiteboard() {
		this.innerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
		this.outerMarketWhiteboard = new HashMap<Integer, IMarketPublicState>(); 
	}

  @Override
  public void postInnerInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    // TODO
  }

  @Override
  public void postOuterInformation(Integer marketID,
      IMarketPublicState marketPublicState) {
    this.outerMarketWhiteboard.put(marketID, marketPublicState); 
  }

  @Override
  public IMarketPublicState getInnerInformation(Integer marketID, Integer timeStep) {
    // TODO
    return null; 
  }

  @Override
  public IMarketPublicState getOuterInformation(Integer marketID) {
    return this.innerMarketWhiteboard.get(marketID); 
  }

  @Override
  public void clear() {
    this.innerMarketWhiteboard.clear();
    this.outerMarketWhiteboard.clear(); 
  }


}
