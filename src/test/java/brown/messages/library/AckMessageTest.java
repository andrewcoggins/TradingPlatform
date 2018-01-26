package brown.messages.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.bidbundle.library.AuctionBidBundle;

/**
 * tests the ack message. 
 * C
 * @author andrew
 *
 */
public class AckMessageTest {
  
  // this method should make sure all the constructors work and that it dispatches
  // correctly with the agent.
  @Test
  public void testAckMessage() { 
    
    // testing only bid and registration messages. If later use Acks for other
    // messages will add those tests in.
    ErrorMessage ackOne = new ErrorMessage(new RegistrationMessage(0), true);
    ErrorMessage ackTwo = new ErrorMessage(new RegistrationMessage(0), false); 
    ErrorMessage ackThree = new ErrorMessage(0, 
        new TradeMessage(0, new AuctionBidBundle(), 0, 0), true); 
    ErrorMessage ackFour = new ErrorMessage(1, 
        new TradeMessage(0, new AuctionBidBundle(), 0, 0), false); 
    
    // test constructors.
    assertEquals(ackOne.REJECTED, true); 
    assertEquals(ackTwo.REJECTED, false);
    assertEquals(ackThree.REJECTED, true);
    assertEquals(ackFour.REJECTED, false);
    assertEquals(ackThree.failedBR, new TradeMessage(0, new AuctionBidBundle(), 0, 0));
    assertEquals(ackFour.failedBR, new TradeMessage(0, new AuctionBidBundle(), 0, 0));
  }
}