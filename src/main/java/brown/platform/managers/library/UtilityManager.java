package brown.platform.managers.library;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.logging.library.ErrorLogging;
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
      ICart goods = entry.getValue().getAllItems(); 
      Double money  = entry.getValue().getMoney(); 
      IGeneralValuation agentValuation = agentValuations.get(agentID); 
      Double goodValues = agentValuation.getValuation(goods);
      System.out.println("UTILITY MANAGER:\n" + 
    		  "GOODS: " + goods + "\n"
    		  + "VALUATION: " + goodValues + "\nMONEY: " + money);
      
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
    Map<Integer, List<Double>> totals = new HashMap<Integer, List<Double>>(); 
    Map<Integer, Double> addedTotals = new HashMap<Integer, Double>();
    Map<Integer, List<Integer>> rank = new HashMap<Integer, List<Integer>>(); 
    for (Integer agentID : this.agentRecords.keySet()) {
      List<Double> money = this.agentRecords.get(agentID); 
      double totalMoney = 0.0; 
      for (double round : money) {
        totalMoney += round; 
      }
      totals.put(agentID, money); 
      addedTotals.put(agentID, totalMoney); 
    }
    
    List<Double> totalsList = new LinkedList<Double>(addedTotals.values()); 
    Collections.sort(totalsList);
    Collections.reverse(totalsList);
    
    for (Integer agentID : this.agentRecords.keySet()) {
      Double addedTotal = addedTotals.get(agentID); 
      Integer place = totalsList.indexOf(addedTotal) + 1; 
      if (rank.containsKey(place)) {
        List<Integer> agents = rank.get(place); 
        agents.add(agentID); 
        rank.put(place, agents); 
      } else {
        List<Integer> anAgent = new LinkedList<Integer>();
        anAgent.add(agentID); 
        rank.put(place, anAgent); 
      }
    }
    
    List<Integer> allPlaces = new LinkedList<Integer>(rank.keySet()); 
    Collections.sort(allPlaces); 
    System.out.println("Utility Manager: Final Utility: ");
    for (Integer place : allPlaces) {
      List<Integer> placeAgents = rank.get(place); 
      for (Integer agentID : placeAgents) {
        System.out.println("[" + place + "]: Agent " + privateToPublic.get(agentID) + " (" + idToName.get(agentID) + ") -> " + addedTotals.get(agentID) + " (" + totals.get(agentID) + ")"); 
      }
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
