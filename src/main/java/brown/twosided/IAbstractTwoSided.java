package brown.twosided;

import brown.agent.AbsAgent;

public interface IAbstractTwoSided extends ITwoSidedAuction {

  public void buy(AbsAgent agent, double shareNum, double sharePrice);
  public void sell(AbsAgent agent, double shareNum, double sharePrice);
  public void cancel(AbsAgent agent, boolean buy, double shareNum, double sharePrice);

}