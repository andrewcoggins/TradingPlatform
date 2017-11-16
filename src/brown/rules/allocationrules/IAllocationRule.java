package brown.rules.allocationrules;

import brown.market.marketstate.IMarketState;

/**
 * an allocation rule decides two things: 
 * @author andrew
 *
 */
public interface IAllocationRule {

  
	public void tick(IMarketState state);
	
  public void setAllocation(IMarketState state);
  
  public void setBidRequest(IMarketState state);

	public void isPrivate(IMarketState state);
	
	public void isOver(IMarketState state);
	
  public void setBundleType(IMarketState state);

	public void withReserve(IMarketState state);

	void isValid(IMarketState state);

	void getAllocationType(IMarketState state);

  void setReport(IMarketState state);

}
