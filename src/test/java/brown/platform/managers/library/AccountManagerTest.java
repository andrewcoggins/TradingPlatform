package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.accounting.IAccount;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.Account;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.managers.IAccountManager;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;

public class AccountManagerTest {
  

  @Test 
  public void testAccountCreation() {
    
    IAccountManager manager = new AccountManager(); 
    
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    
    tList.add(new SimpleTradeable(0)); 
    tList.add(new SimpleTradeable(1)); 
    
    tradeables.put("default", tList); 
    
    IInitialEndowment e = new InitialEndowment(100.0, tradeables); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount accountZero = new Account(0, 100.0, tradeables); 
    IAccount accountOne = new Account(1, 100.0, tradeables); 
    
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
    
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    
    tList.add(new SimpleTradeable(0)); 
    tList.add(new SimpleTradeable(1)); 
    
    tradeables.put("default", tList); 
    
    IInitialEndowment e = new InitialEndowment(100.0, tradeables); 
    
    manager.createAccount(0, e);
    manager.createAccount(1, e);
    
    IAccount act = manager.getAccount(0); 
    
    act.addMoney(1000.0);
    act.addTradeables("more", tList);
    
    manager.setAccount(0, act);
    
    assertEquals(manager.getAccount(0), act); 
    
    Map<Integer, IInitialEndowment> endowments = new HashMap<Integer, IInitialEndowment>(); 
    endowments.put(0, new InitialEndowment(60.0, tradeables)); 
    endowments.put(1, new InitialEndowment(70.0, tradeables)); 
    
    IAccount acctOne = new Account(0, 60.0, tradeables); 
    IAccount acctTwo = new Account(1, 70.0, tradeables); 
    
    manager.reendow(endowments);
    
    assertEquals(manager.getAccount(0), acctOne); 
    assertEquals(manager.getAccount(1), acctTwo); 
        
  }
  
}