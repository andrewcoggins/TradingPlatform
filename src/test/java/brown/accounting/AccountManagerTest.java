package brown.accounting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.accounting.library.Account;
import brown.accounting.library.AccountManager;
import brown.tradeable.library.MultiTradeable;

public class AccountManagerTest {
  // not much to this one. Just test the getter and setter.
  @Test
  public void testAccountManager() { 
    AccountManager testManager = new AccountManager();
    //add some accounts
    List<MultiTradeable> oneGoods = new LinkedList<MultiTradeable>();
    oneGoods.add(new MultiTradeable(0)); 
    oneGoods.add(new MultiTradeable(1));
    Account one = new Account(1);
    one.add(10.0);
    for (MultiTradeable t : oneGoods) {
      one.add(0.0, t);
    }
    testManager.setAccount(1, one);
    List<MultiTradeable> twoGoods = new LinkedList<MultiTradeable>();
    twoGoods.add(new MultiTradeable(2)); 
    twoGoods.add(new MultiTradeable(3));
    Account two = new Account(1);
    two.add(20.0);
    for (MultiTradeable t : twoGoods) {
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