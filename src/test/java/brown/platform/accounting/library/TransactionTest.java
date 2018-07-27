package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.mechanism.tradeable.library.SimpleTradeable;

/**
 * Test for Transaction. 
 * @author andrew
 *
 */
public class TransactionTest {
  
  @Test
  public void testTrasactions() {
    Transaction tOne = new Transaction(0, 1, 0.0, 0, new SimpleTradeable(0)); 
    assertTrue(tOne.TO == 0); 
    assertTrue(tOne.FROM == 1); 
    assertTrue(tOne.PRICE == 0.0); 
    assertTrue(tOne.QUANTITY == 0.0); 
    assertEquals(tOne.TRADEABLE, new SimpleTradeable(0)); 
    
    Transaction tTwo = new Transaction(2, 3, 100.0, 2, new SimpleTradeable(1)); 
    assertTrue(tTwo.TO == 2); 
    assertTrue(tTwo.FROM == 3); 
    assertTrue(tTwo.PRICE == 100.0); 
    assertTrue(tTwo.QUANTITY == 2); 
    assertEquals(tTwo.TRADEABLE, new SimpleTradeable(1)); 
    
    Transaction tThree = tTwo.sanitize(2); 
    assertTrue(tThree.TO == tTwo.TO);
    assertTrue(tThree.FROM == null); 
    Transaction tFour = tTwo.sanitize(0); 
    assertTrue(tFour.TO == null);
    assertTrue(tFour.FROM == null); 
  }
  
  
  public static void main(String[] args) {
    TransactionTest t = new TransactionTest(); 
    t.testTrasactions();
  }
}