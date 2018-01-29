package brown.summary;

import java.util.List;
import java.util.Map;

import brown.accounting.library.Account;


/**
 * extends ISimulationSummary for summaries of auction games.
 * @author andrew
 *
 */
public interface IAuctionSummary extends ISimulationSummary {
  
  /**
   * gets the total utility over all simulations. 
   * @return
   */
  public Map<Integer, Double> getTotalUtility();
  
  /**
   * gets the average utility over all simulations.
   * @return
   */
  public Map<Integer, Double> getAverageUtility();
  
  public void calculateRoundUtility(List<Account> agentAccounts);
  
}