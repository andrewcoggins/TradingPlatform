package brown.twosided;

import java.util.SortedMap;

import brown.agent.AbsAgent;

public interface ITwoSidedPriceSetter extends IAbstractTwoSided {
  
	public void buy(AbsAgent agent, double shareNum, double sharePrice);
	public void sell(AbsAgent agent, double shareNum, double sharePrice);
	public void cancel(AbsAgent agent, boolean buy, double shareNum, double sharePrice);
	
	public double quoteBid(double shareNum, double sharePrice);
	public double quoteAsk(double shareNum, double sharePrice);
	
	public SortedMap<Double, Double> getBuyBook();
	public SortedMap<Double, Double> getSellBook();
	
}
