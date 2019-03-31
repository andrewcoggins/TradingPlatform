package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.tradeable.library.Tradeable;

public class LedgerTest {
  
  @Test
  public void testLedger() {
    
    Ledger ledger = new Ledger(0); 
    assertTrue(ledger.marketId == 0); 
    assertEquals(ledger.transactions, new LinkedList<Transaction>()); 
    assertEquals(ledger.unshared, new LinkedList<Transaction>()); 
    
  }
  
  @Test
  public void testLedgerAdd() {
    
    List<Transaction> transactions = new LinkedList<Transaction>(); 
    Ledger ledger = new Ledger(0); 
    ledger.add(new Transaction(0, 1, 100.0, 1, new Tradeable(0)));
    transactions.add(new Transaction(0, 1, 100.0, 1, new Tradeable(0)));
    assertEquals(ledger.transactions, transactions); 
    assertEquals(ledger.unshared, transactions); 
    
    List<Transaction> transactionsTwo = new LinkedList<Transaction>(); 
    transactionsTwo.add(new Transaction(0, 1, 90.0, 1, new Tradeable(1))); 
    transactionsTwo.add(new Transaction(0, 1, 90.0, 1, new Tradeable(2))); 
    ledger.add(transactionsTwo);
    transactions.add(new Transaction(0, 1, 90.0, 1, new Tradeable(1))); 
    transactions.add(new Transaction(0, 1, 90.0, 1, new Tradeable(2))); 
    
    assertEquals(ledger.transactions, transactions); 
    
    ledger.add(new Tradeable(3), 3, 100.0);
    transactions.add(new Transaction(3, null, 100.0, 1, new Tradeable(3))); 
    assertEquals(ledger.transactions, transactions); 
    assertEquals(ledger.unshared, transactions); 
    
    assertEquals(ledger.getList(), transactions); 
    assertEquals(ledger.getUnshared(), transactions); 
    
    //test sanitize
    
    Ledger sanitizedLedger = ledger.getSanitizedUnshared(0); 
    List<Transaction> tSan = new LinkedList<Transaction>(); 
    for (Transaction t : transactions) {
      tSan.add(t.sanitize(0)); 
    }
    assertEquals(sanitizedLedger.transactions, tSan); 
    assertEquals(sanitizedLedger.unshared, tSan); 
    
    // test clearing
    ledger.clearUnshared();
    assertEquals(ledger.unshared, new LinkedList<Transaction>()); 
    assertEquals(ledger.transactions, transactions); 
    
    
  }

}