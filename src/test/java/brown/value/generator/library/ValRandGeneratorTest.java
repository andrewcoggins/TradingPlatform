package brown.value.generator.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.auction.value.generator.UniformValGenerator;
import brown.mechanism.tradeable.MultiTradeable;

/**
 * test the valrand Generator
 * I
 * @author andrew
 *
 */
public class ValRandGeneratorTest {
  
  @Test
  public void testVrg() {
    int NUMTRIALS = 100;
    UniformValGenerator vrg = new UniformValGenerator();
    MultiTradeable good = new MultiTradeable(0);
    
    for(int i = 0; i < NUMTRIALS; i++) {
    assertTrue(vrg.makeValuation(good).value >= 0.0
        && vrg.makeValuation(good).value <= 1.0);
    }
  }
}