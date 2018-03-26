package brown.value.distribution.library; 

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import brown.value.valuation.IValuation;
import brown.value.valuation.library.XORValuation;

public class SpecValDistributionTest {
  
  @Test
  public void testSpecValDistribution() {
    SpecValDistribution dist = new SpecValDistribution(10, 5, 2.0); 
    dist.giveNumBidders(3); 
    Set<IValuation> vals = dist.sampleAll(); 
    for (IValuation val : vals) {
      XORValuation xVal = (XORValuation) val; 
      assertTrue(xVal.getTradeables().size() == 10);
    }
    assertTrue(vals.size() == 3); 
  }
}