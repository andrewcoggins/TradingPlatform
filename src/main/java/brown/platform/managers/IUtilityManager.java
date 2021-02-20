package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.accounting.IAccount;

/**
 * IUtilityManager stores and manages agent utility totals. 
 * @author andrewcoggins
 *
 */
public interface IUtilityManager {

  /**
   * add agent to utility. 
   * @param agentID
   */
  public void addAgentRecord(Integer agentID); 
  
  /**
   * after a run of a simulation, update current utilities of agents. 
   * creates a record of a simulation-specific utility and an average utility. s
   */
  public void updateUtility(Map<Integer, IAccount> agentAccounts, Map<Integer, IGeneralValuation> agentValuations); 
  
  /**
   * prints utility for a specific simulation run, and the average utility up to the point that this is called. 
   */
  public void logUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName); 
  
  
  public void logLeaderboard(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName);
  
  /**
   * prints the utility after all the simulations have finished. 
   */
  public void logFinalUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName); 
  
  
  /**
   * no more agent records may be added after this is called. 
   */
  public void lock(); 
  
  
  /**
   * get the internal records of the datatype
   * @return
   * Map from agent IDs to utility per simulation. 
   */
  public Map<Integer, List<Double>> getAgentRecords(); 
  
  
}
