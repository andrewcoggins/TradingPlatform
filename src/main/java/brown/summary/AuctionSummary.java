package brown.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.accounting.library.Account;
import brown.tradeable.ITradeable;
import brown.value.valuation.IValuation;

public class AuctionSummary implements IAuctionSummary { 
  
  private Map<Integer, IValuation> privateValuations;
  private Double initialMonies; 
  private Map<Integer, Double> totalUtility;
  private Map<Integer, Double> averageUtility; 
  private int numSimulations;
  
  /**
   * an auction summary takes in the private valuations of agents, and 
   * agents' initial monies.
   * @param privateValuations
   * @param initialMonies
   */
  public AuctionSummary(Map<Integer, IValuation> privateValuations, Double initialMonies) { 
   this.privateValuations = privateValuations;  
   this.totalUtility = new HashMap<Integer, Double>();
   this.averageUtility = new HashMap<Integer, Double>();
   this.numSimulations = 1; 
  }
  
  @Override
  public Map<Integer, Double> getTotalUtility() {
    return this.totalUtility; 
  }
  
  @Override
  public Map<Integer, Double> getAverageUtility() {
    return this.averageUtility; 
  }
  
  // calculates total and average utility for a round of a simulation.
  @Override
  public void calculateRoundUtility(List<Account> agentAccounts) {
    Map<Integer, List<ITradeable>> roundTradeables = new HashMap<Integer, List<ITradeable>>();
    Map<Integer, Double> roundMonies = new HashMap<Integer, Double>();
    for (Account anAccount : agentAccounts) {
      roundTradeables.put(anAccount.ID, anAccount.getGoods());
      roundMonies.put(anAccount.ID, anAccount.getMonies());
    }
    Map<Integer, Double> roundUtilities = new HashMap<Integer, Double>();
    // calculate utility for each agent.
    for (Integer anID : roundTradeables.keySet()) {
      List<ITradeable> someTradeables = roundTradeables.get(anID);
      double tradeableValue = 0.0; 
      for (ITradeable t : someTradeables) {
        IValuation agentValuation = privateValuations.get(anID);
        tradeableValue += agentValuation.getValuation(t);
      }
      double moneyValue = initialMonies - roundMonies.get(anID);
      roundUtilities.put(anID, tradeableValue - moneyValue);
    }
    // integrate into totals. 
    for (Integer anID : roundUtilities.keySet()) {
      // update total utilities.
      if (totalUtility.containsKey(anID)) {
        totalUtility.put(anID, totalUtility.get(anID) + roundUtilities.get(anID));
      }
      // update average utilities.
      if (averageUtility.containsKey(anID)) {
        double avg = totalUtility.get(anID) / (double) this.numSimulations;
        averageUtility.put(anID, avg);
      }
    }
    this.numSimulations++; 
  }
}