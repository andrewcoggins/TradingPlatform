package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.tradeable.library.Tradeable;

public class AccountManagerTest {
  // not much to this one. Just test the getter and setter.
  @Test
  public void testAccountManager() { 
    AccountManager testManager = new AccountManager();
    //add some accounts
    List<Tradeable> oneGoods = new LinkedList<Tradeable>();
    oneGoods.add(new Tradeable(0)); 
    oneGoods.add(new Tradeable(1));
    Account one = new Account(1);
    one.add(10.0);
    for (Tradeable t : oneGoods) {
      one.add(0.0, t);
    }
    testManager.setAccount(1, one);
    List<Tradeable> twoGoods = new LinkedList<Tradeable>();
    twoGoods.add(new Tradeable(2)); 
    twoGoods.add(new Tradeable(3));
    Account two = new Account(1);
    two.add(20.0);
    for (Tradeable t : twoGoods) {
      two.add(0.0, t);
    }
    testManager.setAccount(2, two);
    assertTrue(testManager.containsAcct(1));
    assertTrue(testManager.containsAcct(2));
    List<Account> allAccounts = testManager.getAccounts(); 
    assertTrue(allAccounts.contains(one));
    assertTrue(allAccounts.contains(two)); 
    assertTrue(allAccounts.size() == 2); 
    Account newOne = testManager.getAccount(1); 
    Account newTwo = testManager.getAccount(2); 
    assertEquals(newOne, one); 
    assertEquals(newTwo, two);  
  }
}