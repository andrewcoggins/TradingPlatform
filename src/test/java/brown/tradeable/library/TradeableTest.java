package brown.tradeable.library;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.mechanism.tradeable.library.MultiTradeable;


/**
 * trivial- basically testing that it has an 'equals'
 * @author andrew
 *
 */
public class TradeableTest {
  
  private MultiTradeable testGood = new MultiTradeable();
  private MultiTradeable testGoodTwo = new MultiTradeable(0);
  
  @Test 
  public void testGood() {
    MultiTradeable i = new MultiTradeable();
    MultiTradeable j = new MultiTradeable(0);
    
    assertEquals(testGood, i);
    assertEquals(testGoodTwo, j);
    assertEquals(testGood.ID, i.ID);
    assertEquals(testGoodTwo.ID, j.ID);
  }
}