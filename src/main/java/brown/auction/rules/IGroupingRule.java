package brown.auction.rules;

import java.util.List;

import brown.auction.marketstate.IMarketState;

/**
 * a Grouping rule determines how agents are grouped against each
 * other in an auction. For example, a first price auction may involve
 * one winner with a highest price, or a series of winners who have the highest
 * prices in subgroups of all of the bidders. 
 * @author kerry
 *
 */
public interface IGroupingRule {

  /**
   * This will set the groups field in state, which is a list of lists of agent IDs.
   * @param state market state
   * @param agents private IDs of all agents.
   */
  public void setGrouping(IMarketState state, List<Integer> agents);
  
  /**
   * resets all stored information.
   */
  public void reset();    
}
