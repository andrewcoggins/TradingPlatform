package brown.twosided;

import java.util.SortedMap;

import brown.agent.AbsAgent;

public interface ITwoSidedUnitSetter extends IAbstractTwoSided {
  
	public void buy(AbsAgent agent, int sharePrice);
	public void sell(AbsAgent agent, int sharePrice);
	public void cancel(AbsAgent agent, boolean buy, int sharePrice);
	
	public int quoteBid(int sharePrice);
	public int quoteAsk(int sharePrice);
	
	public SortedMap<Double, Double> getBuyBook();
	public SortedMap<Double, Double> getSellBook();
	
}
