package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.library.Tradeable;


public class AccountTest  {
  
  @Test
  public void testAccount() {
    Account acctOne = new Account(0); 
    List<Tradeable> goods = new LinkedList<Tradeable>();
    goods.add(new Tradeable(0)); 
    goods.add(new Tradeable(1));
    Account acctTwo = new Account(1, 1.0, goods);
    // test that all the accessors work
    assertTrue(acctOne.getMonies() == 0.0);
    assertTrue(acctOne.getGoods().equals(new LinkedList<Tradeable>()));
    List<Tradeable> goodsTwo = new LinkedList<Tradeable>();
    goodsTwo.add(new Tradeable(0)); 
    goodsTwo.add(new Tradeable(1));
    assertTrue(acctTwo.getGoods().equals(goodsTwo));
    assertTrue(acctTwo.getMonies() == 1.0);
    
    // test add methods
    // single tradeable
    acctOne.add(1.0, new Tradeable(0));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<Tradeable> goodsThree = new LinkedList<Tradeable>();
    goodsThree.add(new Tradeable(0)); 
    assertTrue(acctOne.getGoods().equals(goodsThree));
    // adding a list of tradeables
    goodsThree.add(new Tradeable(1));  
    acctOne.add(1.0, goodsThree);
    assertTrue(acctOne.getMonies() == 2.0);
    // to make goodsThree equal to what should be in the account.
    goodsThree.add(1, new Tradeable(0));
    assertEquals(acctOne.getGoods(), goodsThree);
    // clear and continue testing. 
    acctOne.clear();
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Tradeable>());
    Set<Tradeable> someTradeables = new HashSet<Tradeable>();
    someTradeables.add(new Tradeable(0));
    someTradeables.add(new Tradeable(1));
    acctOne.add(1.0, someTradeables);
    assertTrue(acctOne.getMonies() == 1.0); 
    for (Tradeable t : someTradeables) {
      assertTrue(acctOne.getGoods().contains(t)); 
    }
    for (Tradeable t : acctOne.getGoods()) {
      assertTrue(someTradeables.contains(t));
    }
    // test just money add. 
    acctOne.clear(); 
    acctOne.add(10.0); 
    assertTrue(acctOne.getMonies() == 10.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Tradeable>());
    // test remove routine. 
    acctOne.clear(); 
    acctOne.add(1.0, new Tradeable(0)); 
    acctOne.remove(1.0, new Tradeable(0));
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Tradeable>()); 
    acctOne.add(1.0, new Tradeable(0));
    acctOne.remove(1.0, new Tradeable(1));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<Tradeable> tList = new LinkedList<Tradeable>(); 
    tList.add(new Tradeable(0));
    assertEquals(acctOne.getGoods(), tList);
    // test remove routine with set. not sure about case where not every
    // item in set in account. 
    acctOne.clear();
    tList.add(new Tradeable(1)); 
    acctOne.add(1.0, tList);
    acctOne.remove(1.0, tList);
    assertTrue(acctOne.getMonies() == 0.0);
    assertEquals(acctOne.getGoods(), new LinkedList<Tradeable>());
    // test copying account
    acctOne.add(1.0, tList);
    Account acctThree = acctOne.copyAccount(); 
    assertTrue(acctOne.equals(acctThree)); 
    // all good.
  }
}