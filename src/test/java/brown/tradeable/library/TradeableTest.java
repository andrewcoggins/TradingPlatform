package brown.tradeable.library;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.tradeable.library.Good;


/**
 * trivial- basically testing that it has an 'equals'
 * @author andrew
 *
 */
public class TradeableTest {
  
  private Good testGood = new Good();
  private Good testGoodTwo = new Good(0);
  
  @Test 
  public void testGood() {
    Good i = new Good();
    Good j = new Good(0);
    
    assertEquals(testGood, i);
    assertEquals(testGoodTwo, j);
    assertEquals(testGood.ID, i.ID);
    assertEquals(testGoodTwo.ID, j.ID);
  }
}