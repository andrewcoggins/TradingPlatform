package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccount;
import brown.platform.item.ICart;
import brown.platform.managers.IUtilityManager;

/**
 * Keeps track of utility throughout the platform's run. 
 * Stores a list of agent records keeping track of their total and average 
 * utility throughout the platform run. 
 * @author andrewcoggins
 *
 */
public class UtilityManager implements IUtilityManager {
  
  private Map<Integer,List<Double>> agentRecords; 
  private boolean lock; 
  
  public UtilityManager() {
    this.agentRecords = new HashMap<Integer, List<Double>>(); 
    this.lock = false; 
  }
  
  @Override
  public void addAgentRecord(Integer agentID) { 
    if (!this.lock) {
      this.agentRecords.put(agentID, new LinkedList<Double>()); 
    } else {
      ErrorLogging.log("ERROR: Utility Manager: Cannot add agent record, Manager locked"); 
    }
  }
  

  @Override
  public void updateUtility(Map<Integer, IAccount> agentAccounts, Map<Integer, IGeneralValuation> agentValuations) { 
    for (Map.Entry<Integer, IAccount> entry : agentAccounts.entrySet()) {
      Integer agentID = entry.getKey(); 
      ICart goods = entry.getValue().getAllGoods(); 
      Double money  = entry.getValue().getMoney(); 
      IGeneralValuation agentValuation = agentValuations.get(agentID); 
      Double goodValues = agentValuation.getValuation(goods); 
      if (this.agentRecords.containsKey(agentID)) {
        List<Double> agentUtilities = this.agentRecords.get(agentID); 
        agentUtilities.add(money + goodValues); 
      } else {
        ErrorLogging.log("ERROR: UtilityManager: encountered unknown agent ID: " + agentID.toString());
      }
    }
  }

  @Override
  public void logUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName) {
    // TODO Auto-generated method stub

  }

  @Override
  public void logFinalUtility(String inFile, Map<Integer, Integer> privateToPublic, Map<Integer, String> idToName) {
    PlatformLogging.log("Utility Manager: Final Utility: ");
    for (Integer agentID : this.agentRecords.keySet()) {
      List<Double> money = this.agentRecords.get(agentID); 
      String name = privateToPublic.get(agentID).toString();
      if (idToName.containsKey(agentID)) {
        name = idToName.get(agentID); 
      }
      PlatformLogging.log(name + " -> " + money);
    }
  }

  @Override
  public void lock() {
    this.lock = true; 
  }

  @Override
  public Map<Integer, List<Double>> getAgentRecords() {
    return this.agentRecords;
  }

}
