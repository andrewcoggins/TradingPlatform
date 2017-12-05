package brown.accounting;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.tradeable.library.Tradeable;


public class AccountTest  {
  
  @Test
  public void testAccount() {
    Account a = new Account(0);
    assertEquals(a.ID, new Integer(0));
    assertEquals((Double) a.monies, (Double) 0.0); 
    assertEquals(a.tradeables, new LinkedList<>());
    
    a.addAll(0.0, new LinkedList<Tradeable>());
    assertEquals((Double) a.monies, (Double) 0.0); 
    assertEquals(a.tradeables, new LinkedList<>());
    
    Account newA = a.addAll(100.0, new LinkedList<Tradeable>());
    assertEquals((Double) newA.monies, (Double) 100.0);
    assertEquals(newA.tradeables, new LinkedList<>());
    
    Account newATwo = a.addAll(100.0, null);
    assertEquals((Double) newATwo.monies, (Double) 100.0);
    assertEquals(newATwo.tradeables, new LinkedList<>());
    
    List<Tradeable> someTradeables = new LinkedList<Tradeable>();
    for (int i = 0; i < 10; i++)
      someTradeables.add(new Tradeable(i));
    
    Account newAThree = a.addAll(100.0, someTradeables);
    assertEquals(newAThree.tradeables, someTradeables);
    
    
    Account newAFour = new Account(0);
    newAFour.add(100.0, new Tradeable(0)); 
    List<Tradeable> someTradeablesTwo = new LinkedList<Tradeable>();
    someTradeablesTwo.add(new Tradeable(0));
    assertEquals(newAFour.tradeables, someTradeablesTwo);    
    
  }
}