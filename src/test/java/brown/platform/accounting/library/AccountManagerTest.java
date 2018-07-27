package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class AccountManagerTest {
  
  @Test
  public void testAccountManager() {
    
    AccountManager manager = new AccountManager(); 
    List<Account> accountList = new LinkedList<Account>(); 
    for (int i = 0; i < 5; i++) {
      Account newAccount = new Account(i); 
      manager.setAccount(i, newAccount); 
      accountList.add(newAccount); 
    }
    for (int i = 0; i < 5; i++) {
      assertTrue(manager.containsAcct(i)); 
      assertEquals(manager.getAccount(i), accountList.get(i)); 
    }
    assertEquals(manager.getAccounts(), accountList); 
    
    for (int i = 5; i < 10; i++) {
      assertTrue(!manager.containsAcct(i)); 
    }
    manager.reset();
    accountList.clear();
    assertEquals(manager.getAccounts(), accountList); 
    
  }
  
  public static void main(String[] args) {
    
    AccountManagerTest t = new AccountManagerTest(); 
    t.testAccountManager();
  }
}