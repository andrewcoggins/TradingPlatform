package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import brown.tradeable.library.MultiTradeable;

/**
 * tests the transaction class
 * I
 * @author andrew
 *
 */
public class TransactionTest { 
  
  @Test
  public void testTransaction() {
    
    Transaction aTransaction = new Transaction(0, 1, 100.0, 5.0, new MultiTradeable(0)); 
    assertTrue(aTransaction.TO == 0); 
    assertTrue(aTransaction.FROM == 1); 
    assertTrue(aTransaction.PRICE == 100.0); 
    assertTrue(aTransaction.QUANTITY == 5.0); 
    assertTrue(aTransaction.TRADEABLE.equals(new MultiTradeable(0)));  
    Transaction sanitized = aTransaction.sanitize(0); 
    assertEquals(sanitized, new Transaction(0, null, 100.0, 5.0, new MultiTradeable(0)));
  }
}