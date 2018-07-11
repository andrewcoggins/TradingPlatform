package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.MultiTradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;

/**
 * Test for account class.
 * @author andrew
 *
 */
public class AccountTest {
  
  @Test
  public void testGetID() {
    int NUM = 100;
    for (int i = 0; i < NUM; i++) {
      Account anAccount = new Account(i); 
      assertEquals(anAccount.getID(), i); 
    }
  }
  
  @Test
  public void testGetMonies() {
    int NUM = 100; 
    for (int i = 0; i < NUM; i++) {
      Account anAccount = new Account(0, i); 
      assertTrue(anAccount.getMonies() == i); 
    }
  }
  
  @Test
  public void testGetGoods() {
    int NUM = 100; 
    List<ITradeable> goods = new LinkedList<ITradeable>(); 
    for (int i = 0; i < NUM; i++) {
      Account anAccount = new Account(0, 0, goods); 
      goods.add(new SimpleTradeable(i)); 
      assertEquals(anAccount.getGoods(), goods); 
    }
    //test complex tradeable
    List<ITradeable> allTradeables = new LinkedList<ITradeable>();
    Set<ITradeable> complex = new HashSet<ITradeable>(); 
    complex.add(new SimpleTradeable(0)); 
    complex.add(new SimpleTradeable(1)); 
    allTradeables.add(new ComplexTradeable(0, complex)); 
    Account anAccount = new Account(0, 0, allTradeables); 
    assertEquals(anAccount.getGoods(), allTradeables); 
    assertTrue(!anAccount.getGoods().equals(new LinkedList<ITradeable>(complex))); 
    //test multi tradeable
    ITradeable multi = new MultiTradeable(0, 3); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(multi); 
    Account anAccountTwo = new Account(0, 0, tList); 
    assertEquals(anAccountTwo.getGoods(), tList); 
  }
  
  public static void main(String[] args) {
    AccountTest t = new AccountTest(); 
    t.testGetID(); 
    t.testGetMonies();
    t.testGetGoods();
  }
}