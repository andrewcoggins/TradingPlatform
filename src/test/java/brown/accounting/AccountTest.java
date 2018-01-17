package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.ITradeable;
import brown.tradeable.library.Good;


public class AccountTest  {
  
  @Test
  public void testAccount() {
    Account acctOne = new Account(0); 
    List<Good> goods = new LinkedList<Good>();
    goods.add(new Good(0)); 
    goods.add(new Good(1));
    Account acctTwo = new Account(1);
    acctTwo.add(1.0);
    for (Good good : goods) {
      acctTwo.add(0.0, good);
    }
    // test that all the accessors work
    assertTrue(acctOne.getMonies() == 0.0);
    assertTrue(acctOne.getGoods().equals(new LinkedList<Good>()));
    List<Good> goodsTwo = new LinkedList<Good>();
    goodsTwo.add(new Good(0)); 
    goodsTwo.add(new Good(1));
    assertTrue(acctTwo.getGoods().equals(goodsTwo));
    assertTrue(acctTwo.getMonies() == 1.0);
    
    // test add methods
    // single tradeable
    acctOne.add(1.0, new Good(0));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<Good> goodsThree = new LinkedList<Good>();
    goodsThree.add(new Good(0)); 
    assertTrue(acctOne.getGoods().equals(goodsThree));
    // adding a list of tradeables
    goodsThree.add(new Good(1)); 
    for (Good t : goodsThree) {
      acctOne.add(1.0, t);
    }
    assertTrue(acctOne.getMonies() == 3.0);
    // to make goodsThree equal to what should be in the account.
    goodsThree.add(1, new Good(0));
    assertEquals(acctOne.getGoods(), goodsThree);
    // clear and continue testing. 
    acctOne.clear();
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Good>());
    Set<Good> someTradeables = new HashSet<Good>();
    someTradeables.add(new Good(0));
    someTradeables.add(new Good(1));
    for (Good t : someTradeables) {
      acctOne.add(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0); 
    for (Good t : someTradeables) {
      assertTrue(acctOne.getGoods().contains(t)); 
    }
    for (ITradeable t : acctOne.getGoods()) {
      assertTrue(someTradeables.contains((Good) t));
    }
    // test just money add. 
    acctOne.clear(); 
    acctOne.add(10.0); 
    assertTrue(acctOne.getMonies() == 10.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Good>());
    // test remove routine. 
    acctOne.clear(); 
    acctOne.add(1.0, new Good(0)); 
    acctOne.remove(1.0, new Good(0));
    assertTrue(acctOne.getMonies() == 0.0); 
    assertEquals(acctOne.getGoods(), new LinkedList<Good>()); 
    acctOne.add(1.0, new Good(0));
    acctOne.remove(1.0, new Good(1));
    assertTrue(acctOne.getMonies() == 1.0); 
    List<Good> tList = new LinkedList<Good>(); 
    tList.add(new Good(0));
    assertEquals(acctOne.getGoods(), tList);
    // test remove routine with set. not sure about case where not every
    // item in set in account. 
    acctOne.clear();
    tList.add(new Good(1)); 
    for (Good t : tList) {
      acctOne.add(0.0, t);
      acctOne.remove(0.0, t);
    }
    assertTrue(acctOne.getMonies() == 0.0);
    assertEquals(acctOne.getGoods(), new LinkedList<Good>());
    // test copying account
    for (Good t : tList){ 
      acctOne.add(1.0, t);
    }
    Account acctThree = acctOne.copyAccount(); 
    assertTrue(acctOne.equals(acctThree)); 
    //try the ITradeables
    List<ITradeable> aList = new LinkedList<ITradeable>();
    aList.add(new Good(0));
    Account acctFour = new Account(0);
    acctFour.add(5.0, aList);
    assertTrue(acctFour.getMonies() == 5.0);
    assertEquals(acctFour.getGoods(), aList);
    assertEquals((Good) acctFour.getGoods().get(0), aList.get(0));
    // all good.
  }
}