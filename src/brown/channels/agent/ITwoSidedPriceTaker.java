package brown.channels.agent;

import brown.agent.AbsAgent;

public interface ITwoSidedPriceTaker extends IAbstractTwoSided {
	public void buy(AbsAgent agent, double shareNum, double maxPrice);
	public void sell(AbsAgent agent, double shareNum, double maxPrice);
	public void cancel(AbsAgent agent, boolean buy, double shareNum, double maxPrice);
	
	public double quoteBid(double shareNum);
	public double quoteAsk(double shareNum);
}
