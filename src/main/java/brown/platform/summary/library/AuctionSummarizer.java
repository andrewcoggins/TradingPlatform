package brown.platform.summary.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.valuation.IValuation;
import brown.auction.value.valuation.library.SpecValValuation;
import brown.logging.library.Logging;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.platform.accounting.library.Account;
import brown.platform.summary.IAuctionSummarizer;

/**
 * Auction Summarizer calculcates total and average utility across rounds.
 * @author acoggins
 *
 */
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
  public AuctionSummarizer(Set<Integer> privateIDs) { 
   this.totalUtility = new HashMap<Integer, Double>();
   this.averageUtility = new HashMap<Integer, Double>();
   for (Integer id : privateIDs) { 
     this.totalUtility.put(id, 0.0); 
     this.totalUtility.put(id, 0.0);
   }
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
  public void collectInformation(List<Account> agentAccounts, Map<Integer, IValuation> privateValuations) {
    Map<Integer, List<ITradeable>> roundTradeables = new HashMap<Integer, List<ITradeable>>();
    Map<Integer, Double> roundMonies = new HashMap<Integer, Double>();
    for (Account anAccount : agentAccounts) {
      roundTradeables.put(anAccount.ID, anAccount.getGoods());
      roundMonies.put(anAccount.ID, anAccount.getMonies());
    }        
    Map<Integer, Double> roundUtilities = new HashMap<Integer, Double>();
    // calculate utility as just money if there are no valuations.
    if (privateValuations.isEmpty()) {
      for (Integer anID : roundTradeables.keySet()) {
        roundUtilities.put(anID, roundMonies.get(anID));
      }
    } else {
      // calculate utility for each agent (with valuations).
      for (Integer anID : roundTradeables.keySet()) {
        List<ITradeable> someTradeables = roundTradeables.get(anID);
        double tradeableValue = 0.0;         
        if (privateValuations.get(anID) instanceof SpecValValuation){
          List<ITradeable> flattened_t = new LinkedList<ITradeable>();
          for (ITradeable t : someTradeables) {
            flattened_t.addAll(t.flatten());
          }
          Set<ITradeable> tSet = new HashSet<ITradeable>(flattened_t);
          SpecValValuation svVal = (SpecValValuation) privateValuations.get(anID);
          tradeableValue += svVal.queryValue(new ComplexTradeable(0,tSet));
        } else {
          for (ITradeable t : someTradeables) {
            IValuation agentValuation = privateValuations.get(anID);
            tradeableValue += agentValuation.getValuation(t);
          }        
        }                
        roundUtilities.put(anID, tradeableValue + roundMonies.get(anID));
      }
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
    Logging.log("Total:" + this.totalUtility);
    this.numSimulations++; 
  }
}