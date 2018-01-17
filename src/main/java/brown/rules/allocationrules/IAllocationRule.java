package brown.rules.allocationrules;

import brown.market.marketstate.ICompleteState;

/**
 * an allocation rule decides two things: 
 * 
 * @author andrew
 */
public interface IAllocationRule {

	public void tick(ICompleteState state);
	
  public void setAllocation(ICompleteState state);
  
  public void setBidRequest(ICompleteState state);

	public void isPrivate(ICompleteState state);
	
	public void isOver(ICompleteState state);
	
  public void setBundleType(ICompleteState state);

	public void withReserve(ICompleteState state);

	void isValid(ICompleteState state);

	void getAllocationType(ICompleteState state);

  void setReport(ICompleteState state);

}
