package brown.auction.rules.termination;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketState;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.termination.OneShotTermination;
import brown.communication.messages.ITradeMessage;

public class OneShotTerminationTest {

  @Test
  public void testOneShotTermination() {
   
    IMarketState state = new MarketState(); 
    ITerminationCondition tCondition = new OneShotTermination(); 
    
    assertTrue(state.isOpen()); 
    
    tCondition.checkTerminated(state, new LinkedList<ITradeMessage>());
    
    assertTrue(state.isOpen()); 
    
    state.tick();
    
    tCondition.checkTerminated(state, new LinkedList<ITradeMessage>());
    
    assertTrue(!state.isOpen()); 
    
  }
}
