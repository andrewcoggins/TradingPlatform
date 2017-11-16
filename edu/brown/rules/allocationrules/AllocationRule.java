package brown.rules.allocationrules;

import brown.marketinternalstates.MarketInternalState;

/**
 * an allocation rule decides two things: 
 * @author andrew
 *
 */
public interface AllocationRule {

  
	public void tick(MarketInternalState state);
	
  public void setAllocation(MarketInternalState state);
  
  public void setBidRequest(MarketInternalState state);

	public void isPrivate(MarketInternalState state);
	
	public void isOver(MarketInternalState state);
	
  public void setBundleType(MarketInternalState state);

	public void withReserve(MarketInternalState state);

	void isValid(MarketInternalState state);

	void getAllocationType(MarketInternalState state);

  void setReport(MarketInternalState state);

}
