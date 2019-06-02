package brown.auction.rules.termination.onesided;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.ITerminationCondition;

public class OneShotTerminationTest {

  @Test
  public void testOneShotTermination() {
   
    IMarketState state = new MarketState(); 
    ITerminationCondition tCondition = new OneShotTermination(); 
    
    assertTrue(state.isOpen()); 
    
    tCondition.checkTerminated(state);
    
    assertTrue(state.isOpen()); 
    
    state.tick();
    
    tCondition.checkTerminated(state);
    
    assertTrue(!state.isOpen()); 
    
  }
}
