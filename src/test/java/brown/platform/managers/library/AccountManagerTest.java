package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.accounting.IAccount;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.Account;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IAccountManager;

public class AccountManagerTest {
  

  @Test 
  public void testAccountCreation() {
    
    IAccountManager manager = new AccountManager(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("default", 2)); 
    
    ICart aCart = new Cart(items); 
    IInitialEndowment e = new InitialEndowment(100.0, aCart); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount accountZero = new Account(0, 100.0, aCart); 
    IAccount accountOne = new Account(1, 100.0, aCart); 
    
    assertTrue(manager.containsAccount(0)); 
    assertTrue(manager.containsAccount(1)); 
    assertEquals(manager.getAccount(0), accountZero); 
    assertEquals(manager.getAccount(1), accountOne); 
    
    List<IAccount> accountList = new LinkedList<IAccount>(); 
    accountList.add(accountZero); 
    accountList.add(accountOne); 
    
    assertEquals(manager.getAccounts(), accountList); 
    
    manager.lock();
    
    manager.createAccount(2, e);
    
    assertTrue(!manager.containsAccount(2)); 
    
  }
  
  @Test 
  public void testAccountManager() {
    
    IAccountManager manager = new AccountManager(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("default", 2)); 
    
    ICart aCart = new Cart(items); 
    IInitialEndowment e = new InitialEndowment(100.0, aCart); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount act = manager.getAccount(0); 
    
    act.addMoney(1000.0);
    
    act.addTradeables(new Item("b", 2));
    
    manager.setAccount(0, act);
    
    assertEquals(manager.getAccount(0), act); 
    
    IAccount acctOne = new Account(0, 60.0, aCart); 
    IAccount acctTwo = new Account(1, 70.0, aCart); 
    
    manager.reendow(0, new InitialEndowment(60.0, aCart));
    manager.reendow(1, new InitialEndowment(70.0, aCart));
    
    assertEquals(manager.getAccount(0), acctOne); 
    assertEquals(manager.getAccount(1), acctTwo); 
        
  }
  
}