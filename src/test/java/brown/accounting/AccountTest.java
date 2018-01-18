package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;


public class AccountTest  {
  
  @Test
  public void testAccount() {
    Account acctOne = new Account(0); 
    List<MultiTradeable> goods = new LinkedList<MultiTradeable>();
    goods.add(new MultiTradeable(0)); 
    goods.add(new MultiTradeable(1));
    Account acctTwo = new Account(1);
    acctTwo.add(1.0);
    for (MultiTradeable good : goods) {
      acctTwo.add(0.0, good);
    }
    // test that all the accessors work
    assertTrue(acctOne.getMonies() == 0.0);
    assertTrue(acctOne.getGoods().equals(new LinkedList<MultiTradeable>()));
    List<MultiTradeable> goodsTwo = new LinkedList<MultiTradeable>();
    goodsTwo.add(new MultiTradeable(0)); 
    goodsTwo.add(new MultiTradeable(1));
    assertTrue(acctTwo.getGoods().equals(goodsTwo));
    assertTrue(acctTwo.getMonies() == 1.0);
    
    // test add methods
    // single tradeable
    acctOne.add(1.0, new MultiTradeable(0));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<MultiTradeable> goodsThree = new LinkedList<MultiTradeable>();
    goodsThree.add(new MultiTradeable(0)); 
    assertTrue(acctOne.getGoods().equals(goodsThree));
    // adding a list of tradeables
    goodsThree.add(new MultiTradeable(1)); 
    for (MultiTradeable t : goodsThree) {
      acctOne.add(1.0, t);
    }
    assertTrue(acctOne.getMonies() == 3.0);
    // to make goodsThree equal to what should be in the account.
    goodsThree.add(1, new MultiTradeable(0));
    assertEquals(acctOne.getGoods(), goodsThree);
    // clear and continue testing. 
    acctOne.clear();
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<MultiTradeable>());
    Set<MultiTradeable> someTradeables = new HashSet<MultiTradeable>();
    someTradeables.add(new MultiTradeable(0));
    someTradeables.add(new MultiTradeable(1));
    for (MultiTradeable t : someTradeables) {
      acctOne.add(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0); 
    for (MultiTradeable t : someTradeables) {
      assertTrue(acctOne.getGoods().contains(t)); 
    }
    for (ITradeable t : acctOne.getGoods()) {
      assertTrue(someTradeables.contains((MultiTradeable) t));
    }
    // test just money add. 
    acctOne.clear(); 
    acctOne.add(10.0); 
    assertTrue(acctOne.getMonies() == 10.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<MultiTradeable>());
    // test remove routine. 
    acctOne.clear(); 
    acctOne.add(1.0, new MultiTradeable(0)); 
    acctOne.remove(1.0, new MultiTradeable(0));
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<MultiTradeable>()); 
    acctOne.add(1.0, new MultiTradeable(0));
    acctOne.remove(1.0, new MultiTradeable(1));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<MultiTradeable> tList = new LinkedList<MultiTradeable>(); 
    tList.add(new MultiTradeable(0));
    assertEquals(acctOne.getGoods(), tList);
    // test remove routine with set. not sure about case where not every
    // item in set in account. 
    acctOne.clear();
    tList.add(new MultiTradeable(1)); 
    for (MultiTradeable t : tList) {
      acctOne.add(0.0, t);
      acctOne.remove(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0);
    assertEquals(acctOne.getGoods(), new LinkedList<MultiTradeable>());
    // test copying account
    for (MultiTradeable t : tList){ 
      acctOne.add(1.0, t);
    }
    Account acctThree = acctOne.copyAccount(); 
    assertTrue(acctOne.equals(acctThree)); 
    //try the ITradeables
    List<ITradeable> aList = new LinkedList<ITradeable>();
    aList.add(new MultiTradeable(0));
    Account acctFour = new Account(0);
    acctFour.add(5.0, aList);
    assertTrue(acctFour.getMonies() == 5.0);
    assertEquals(acctFour.getGoods(), aList);
    assertEquals((MultiTradeable) acctFour.getGoods().get(0), aList.get(0));
    // all good.
  }
  
}