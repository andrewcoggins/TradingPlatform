package brown.accounting;

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
import brown.platform.accounting.library.Account;


public class AccountTest  {
  
  @Test
  public void testAccount() {
    
//    Account acctOne = new Account(1); 
//    List<ITradeable> goods = new LinkedList<ITradeable>();
//    goods.add(new SimpleTradeable(1));   
//    goods.add(new SimpleTradeable(2));          
//    goods.add(new SimpleTradeable(2));           
//    goods.add(new MultiTradeable(3,3));                      
//    List<ITradeable> complex = new LinkedList<ITradeable>();
//    complex.add(new SimpleTradeable(4));
//    complex.add(new MultiTradeable(5,5));
//    List<ITradeable> innerComplex = new LinkedList<ITradeable>();
//    innerComplex.add(new MultiTradeable(4,3));
//    innerComplex.add(new MultiTradeable(7,7));
//    complex.add(new MultiTradeable(6,3));
    
    
    Account acctOne = new Account(0); 
    Account acctTwo = new Account(1);
    acctTwo.add(1.0);
    List<ITradeable> goodsOne = new LinkedList<ITradeable>();
    goodsOne.add(new SimpleTradeable(0));   
    goodsOne.add(new SimpleTradeable(1));       
    for (ITradeable good : goodsOne) {
      acctTwo.add(0.0, good);
    }
    
    // test that all the accessors work
    assertTrue(acctOne.getID() == 1);    
    assertTrue(acctOne.getMonies() == 0.0);
    assertTrue(acctOne.getGoods().equals(new LinkedList<ITradeable>()));

    List<ITradeable> goodsTwo = new LinkedList<ITradeable>();
    goodsTwo.add(new SimpleTradeable(0)); 
    goodsTwo.add(new SimpleTradeable(1));
    assertTrue(acctOne.getID() == 2);    
    assertTrue(acctTwo.getMonies() == 1.0);    
    assertTrue(acctTwo.getGoods().equals(goodsOne));
    
    // test add methods
    
    // just money
    acctOne.add(1.0);
    assertTrue(acctOne.getMonies() == 1.0); 

    // Clear
    acctOne.clear();
    assertTrue(acctOne.getMonies() == 0.0);
    assertTrue(acctOne.getGoods().equals(new LinkedList<ITradeable>()));
   
    // single ITradeable    
    acctOne.add(1.0, new MultiTradeable(0,5));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<ITradeable> goodsThree = new LinkedList<ITradeable>();
    goodsThree.add(new MultiTradeable(0,5)); 
    assertTrue(acctOne.getGoods().equals(goodsThree));
    acctOne.clear();
    
    // List of ITradeables
    List<ITradeable> goodsFour = new LinkedList<ITradeable>();
    goodsFour.add(new SimpleTradeable(0));
    goodsFour.add(new MultiTradeable(2,2));
    List<ITradeable> inner = new LinkedList<ITradeable>();
    inner.add(new SimpleTradeable(3));
    inner.add(new MultiTradeable(4,4));    
    goodsFour.add(new ComplexTradeable(inner));
   
    List<ITradeable> toAdd = new LinkedList<ITradeable>;
    
    acctOne.add()
    
    // adding a list of tradeables
    goodsThree.add(new SimpleTradeable(1)); 
    for (SimpleTradeable t : goodsThree) {
      acctOne.add(1.0, t);
    }
    assertTrue(acctOne.getMonies() == 3.0);
    // to make goodsThree equal to what should be in the account.
    goodsThree.add(1, new SimpleTradeable(0));
    assertEquals(acctOne.getGoods(), goodsThree);
    // clear and continue testing. 
    acctOne.clear();
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<SimpleTradeable>());
    Set<SimpleTradeable> someTradeables = new HashSet<SimpleTradeable>();
    someTradeables.add(new SimpleTradeable(0));
    someTradeables.add(new SimpleTradeable(1));
    for (SimpleTradeable t : someTradeables) {
      acctOne.add(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0); 
    for (SimpleTradeable t : someTradeables) {
      assertTrue(acctOne.getGoods().contains(t)); 
    }
    for (ITradeable t : acctOne.getGoods()) {
      assertTrue(someTradeables.contains((SimpleTradeable) t));
    }
    // test just money add. 
    acctOne.clear(); 
    acctOne.add(10.0); 
    assertTrue(acctOne.getMonies() == 10.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<SimpleTradeable>());
    // test remove routine. 
    acctOne.clear(); 
    acctOne.add(1.0, new SimpleTradeable(0)); 
    acctOne.remove(1.0, new SimpleTradeable(0));
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<SimpleTradeable>()); 
    acctOne.add(1.0, new SimpleTradeable(0));
    acctOne.remove(1.0, new SimpleTradeable(1));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<SimpleTradeable> tList = new LinkedList<SimpleTradeable>(); 
    tList.add(new SimpleTradeable(0));
    assertEquals(acctOne.getGoods(), tList);
    // test remove routine with set. not sure about case where not every
    // item in set in account. 
    acctOne.clear();
    tList.add(new SimpleTradeable(1)); 
    for (SimpleTradeable t : tList) {
      acctOne.add(0.0, t);
      acctOne.remove(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0);
    assertEquals(acctOne.getGoods(), new LinkedList<SimpleTradeable>());
    // test copying account
    for (SimpleTradeable t : tList){ 
      acctOne.add(1.0, t);
    }
    Account acctThree = acctOne.copyAccount(); 
    assertTrue(acctOne.equals(acctThree)); 
    //try the ITradeables
    List<ITradeable> aList = new LinkedList<ITradeable>();
    aList.add(new SimpleTradeable(0));
    Account acctFour = new Account(0);
    acctFour.add(5.0, aList);
    assertTrue(acctFour.getMonies() == 5.0);
    assertEquals(acctFour.getGoods(), aList);
    assertEquals((SimpleTradeable) acctFour.getGoods().get(0), aList.get(0));
    // all good.
  }
  
}