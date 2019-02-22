package brown.communication.bid.library;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GameBidTest {

  @Test
  public void testGameBid() {
    GameBid g = new GameBid(0); 
    assertTrue(g.action == 0); 
    
    GameBid gTwo = new GameBid(9237498); 
    assertTrue(gTwo.action == 9237498); 
    
    GameBid gThree = new GameBid(-12); 
    assertTrue(gThree.action == -12); 
  }
}
