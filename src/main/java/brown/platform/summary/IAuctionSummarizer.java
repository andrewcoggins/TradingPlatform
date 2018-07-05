package brown.platform.summary;

import java.util.Map;



/**
 * extends ISimulationSummary for summaries of auction games.
 * @author andrew
 *
 */
public interface IAuctionSummarizer extends ISimulationSummarizer {
  
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

  /**
   * gets the number of simulations so far.
   * @return
   */
  public int getSimulations();
}