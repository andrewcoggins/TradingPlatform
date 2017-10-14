package brown.channels;

import brown.agent.Agent;

public interface IAbstractTwoSided extends ITwoSidedAuction {
  
  public void buy(Agent agent, double shareNum, double sharePrice);
  public void sell(Agent agent, double shareNum, double sharePrice);
  public void cancel(Agent agent, boolean buy, double shareNum, double sharePrice);

}