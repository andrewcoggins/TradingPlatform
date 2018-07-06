package brown.messages.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.platform.messages.library.RegistrationMessage;

/**
 * tests the registration message. 
 * C
 * @author andrew
 *
 */
public class RegistrationMessageTest {
  // nothing to test! 
  @Test
  public void testRegistrationMessage() {
    RegistrationMessage rm = new RegistrationMessage(0); 
    assertEquals(rm.getID(), new Integer(0)); 
  }
}