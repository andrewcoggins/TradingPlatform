package brown.summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.accounting.library.Account;
import brown.tradeable.ITradeable;
import brown.value.valuation.IValuation;

public class AuctionSummarizer implements IAuctionSummarizer { 
  
  private Map<Integer, Double> totalUtility;
  private Map<Integer, Double> averageUtility; 
  private int numSimulations;
  
  /**
   * an auction summary takes in the private valuations of agents, and 
   * agents' initial monies.
   * @param privateValuations
   * @param initialMonies
   */
  public AuctionSummarizer() { 
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
  
  @Override
  public int getSimulations() {
    return this.numSimulations;
  }
  
  // calculates total and average utility for a round of a simulation.
  @Override
  public void collectInformation(List<Account> agentAccounts,
      Map<Integer, IValuation> privateValuations, double initialMonies) {
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
      roundUtilities.put(anID, tradeableValue + roundMonies.get(anID));
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