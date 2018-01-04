package brown.messages.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * test for the lemonade report class. 
 * C
 * @author andrew
 *
 */
public class LemonadeReportMessageTest {
  
  @Test
  public void testLemondeReportMessage() { 
    // not much to do here. 
    int[] slots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}; 
    LemonadeReportMessage lrm = new LemonadeReportMessage(slots); 
    for(int i = 0; i < 11; i++) {
      assertEquals(lrm.getCount(i), i); 
    }
  }
}