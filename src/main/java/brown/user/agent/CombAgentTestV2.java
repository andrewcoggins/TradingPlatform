package brown.user.agent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import brown.logging.Logging;

/**
 * test agent for final combinatorial project.
 * @author kerry
 *
 */
public class CombAgentTestV2 extends AbsCombinatorialProjectAgentV2 {
  private int low; 
  private int high;
  
  
  public CombAgentTestV2(String host, int port, String name, int low, int high) {
    super(host, port, name);
    this.low = low;
    this.high = high;
  }

  @Override
  public Set<Integer> onBidRound() {
    Logging.log("Prices: " + Arrays.toString(getPrices()));
    Set<Integer> toBid = new HashSet<Integer>();
    for (int i = low; i <= high; i++){
      toBid.add(i);
    }            
    if (getBundlePrice(toBid) < queryValue(toBid)){
      Logging.log("Bidding on " + toBid + " for price " + getBundlePrice(toBid));
      Logging.log("Value for bundle: " + queryValue(toBid));    
      Long currtime = System.currentTimeMillis();
      Set<Set<Integer>> bundles = new HashSet<Set<Integer>>();    
      for (int j = low + 1; j <= high; j++){
        Set<Integer> asd = new HashSet<Integer>();        
        for (int i = low; i <= j; i++){
          asd.add(i);
        }           
        bundles.add(asd);
      }
      int i =0;
      while (System.currentTimeMillis() - currtime < 1000){
        i++;
        sampleValues(bundles);
      }
      Logging.log("Sampled " + i + " times");
      return toBid;      
    } else {
      return new HashSet<Integer>();
    }
  }

  @Override
  public void onBidResults(double[] allocations) {
    Set<Integer> winning = new HashSet<Integer>();
    Set<Integer> pwinning = new HashSet<Integer>();
    
    for (int i = 0; i< this.allocations.length; i++){      
      if (allocations[i] == 1){
        winning.add(i);
      } else if (allocations[i] > 0){
        pwinning.add(i);
      }      
    }
    Logging.log("Winning: " + winning);
    Logging.log("Partially winning: " + pwinning);    
  }

  @Override
  public void onAuctionStart() {
    Logging.log("Agent "  +this.ID + " with Alpha: " + this.valuation.getAlpha());
    Long currtime = System.currentTimeMillis();
    while (System.currentTimeMillis() - currtime < 10000){      
    }
  }

  @Override
  public void onAuctionEnd(Set<Integer> finalBundle) {
    Logging.log("Value final bundle at " + queryValue(finalBundle));
  }

  public static void main(String[] args) {
    new CombAgentTestV2("localhost", 2121, "agent1",15,20);
    new CombAgentTestV2("localhost", 2121, "agent2", 16,21);
    new CombAgentTestV2("localhost", 2121, "agent1",15,20);
    new CombAgentTestV2("localhost", 2121, "agent2", 16,21);
    new CombAgentTestV2("localhost", 2121, "agent1",15,20);
    new CombAgentTestV2("localhost", 2121, "agent2", 16,21);    
    while (true) {}
  }
}
