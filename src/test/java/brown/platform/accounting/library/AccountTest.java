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
    //add with complexTradeables. 
    
  }
  
  @Test
  public void testAdd() {
    
    Account a = new Account(0); 
    a.add(100.0);
    assertTrue(a.getMonies() == 100.0); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(new SimpleTradeable(0)); 
    a.add(0.0, new SimpleTradeable(0)); 
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 100.0); 
    tList.add(new SimpleTradeable(1)); 
    a.add(10.0, new SimpleTradeable(1));
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 110.0); 
    List<ITradeable> tListTwo = new LinkedList<ITradeable>(); 
    tListTwo.add(new SimpleTradeable(2)); 
    tListTwo.add(new SimpleTradeable(3)); 
    tList.addAll(tListTwo); 
    a.add(-20.0, tListTwo);
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 90.0); 
    Set<ITradeable> tSet = new HashSet<ITradeable>(); 
    tSet.add(new SimpleTradeable(4)); 
    tList.add(new SimpleTradeable(4)); 
    a.add(-50.0, tSet);
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 40.0);
    a.add(100.0);
    assertTrue(a.getMonies() == 140.0);
    a.clear();
    assertEquals(a.getGoods(), new LinkedList<ITradeable>()); 
    assertTrue(a.getMonies() == 0.0);
    
    //complex tradeables are treated the same as simple tradeables in add.
    Set<ITradeable> cList = new HashSet<ITradeable>(); 
    cList.add(new SimpleTradeable(0)); 
    cList.add(new SimpleTradeable(1)); 
    ComplexTradeable c = new ComplexTradeable(0, cList); 
    a.add(10.0, c);
    List<ITradeable> tradeables = new LinkedList<ITradeable>(); 
    tradeables.add(c); 
    assertEquals(a.getGoods(), tradeables);
    assertTrue(a.getMonies() == 10.0); 
    a.add(10.0, new SimpleTradeable(4));
    tradeables.add(new SimpleTradeable(4)); 
    assertEquals(a.getGoods(), tradeables); 
    assertTrue(a.getMonies() == 20.0); 
    
  }
  
  @Test 
  public void testRemove() {
    Account a = new Account(0); 
    a.add(100.0);
    a.remove(10.0);
    assertTrue(a.getMonies() == 90.0); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(new SimpleTradeable(0)); 
    tList.add(new SimpleTradeable(1)); 
    a.add(0.0, tList);
    a.remove(10.0, new SimpleTradeable(1));
    tList.remove(new SimpleTradeable(1)); 
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 80.0); 
    List<ITradeable> tListTwo = new LinkedList<ITradeable>(); 
    a.add(0.0, new SimpleTradeable(1));
    a.add(0.0, new SimpleTradeable(2));
    tListTwo.add(new SimpleTradeable(2)); 
    tList.add(new SimpleTradeable(1)); 
    a.remove(10.0, tListTwo);
    assertEquals(a.getGoods(), tList);
    assertTrue(a.getMonies() == 70.0);
    Set<ITradeable> tSet = new HashSet<ITradeable>(); 
    tSet.add(new SimpleTradeable(1)); 
    tList.remove(new SimpleTradeable(1)); 
    a.remove(10.0, tSet);
    assertEquals(a.getGoods(), tList); 
    assertTrue(a.getMonies() == 60.0); 
    
    a.clear();
    assertEquals(a.getGoods(), new LinkedList<ITradeable>()); 
    assertTrue(a.getMonies() == 0.0); 
    
    //complex Tradeables
    //can add a complex tradeable and remove simple tradeables. 
    //that makes sense, right? 
    Set<ITradeable> b = new HashSet<ITradeable>(); 
    b.add(new SimpleTradeable(0)); 
    b.add(new SimpleTradeable(1)); 
    b.add(new SimpleTradeable(2)); 
    ComplexTradeable c2 = new ComplexTradeable(0, b); 
    a.add(0.0, c2);
    List<ITradeable> testList=  new LinkedList<ITradeable>(); 
    testList.add(new SimpleTradeable(0)); 
    testList.add(new SimpleTradeable(1)); 
    a.remove(0.0, testList);
    testList.clear(); 
    testList.add(new SimpleTradeable(2)); 
    assertEquals(a.getGoods(), testList); 
    assertTrue(a.getMonies() == 0.0); 
  }
  
  
  public static void main(String[] args) {
    AccountTest t = new AccountTest(); 
    t.testGetID(); 
    t.testGetMonies();
    t.testGetGoods();
    t.testAdd();
    t.testRemove();
  }
}