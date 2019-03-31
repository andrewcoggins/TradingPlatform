package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.accounting.IAccount;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

/**
 * Test for account class.
 * @author andrew
 *
 */
public class AccountUpdateTest {
  
  @Test
  public void testAccountInit() {
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>();
    
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    
    tList.add(new Tradeable(0)); 
    
    tList.add(new Tradeable(1)); 
    
    tradeables.put("default", tList);
    
    IAccount a = new Account(100, 90.0, tradeables);
    
    assertTrue(a.getID() == 100); 
    
    assertTrue(a.getMoney() == 90.0); 
    
    assertEquals(a.getAllGoods(), tradeables); 
    
    assertEquals(a.getGoods("default"), tradeables.get("default")); 
  }
  
  
  @Test
  public void testAccount() {
    
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
    
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    
    tList.add(new Tradeable(0)); 
    
    tList.add(new Tradeable(1)); 
    
    tradeables.put("default", tList);
    
    IAccount a = new Account(5, 100.0, tradeables); 
    
    // test money
    
    a.addMoney(50.0);
    
    assertTrue(a.getMoney() == 150.0);
    
    a.removeMoney(90.0);
    
    assertTrue(a.getMoney() == 60.0);
    
    // test tradeables
    
    List<ITradeable> moreTradeables = new LinkedList<ITradeable>(); 
    
    moreTradeables.add(new Tradeable(2)); 
    
    a.addTradeables("default", moreTradeables);
    
    List<ITradeable> tradeablesList = tradeables.get("default"); 
    
    tradeablesList.add(new Tradeable(2)); 
    
    tradeables.put("default", tradeablesList); 
    
    assertEquals(a.getGoods("default"), tradeables.get("default")); 
    
    assertEquals(a.getAllGoods(), tradeables); 
    
    List<ITradeable> evenMoreTradeables = new LinkedList<ITradeable>(); 
    
    evenMoreTradeables.add(new Tradeable(3)); 
    
    a.addTradeables("other", evenMoreTradeables);
    
    assertEquals(a.getGoods("other"), evenMoreTradeables); 
    
    tradeables.put("other", evenMoreTradeables);
    
    assertEquals(a.getAllGoods(), tradeables); 
    
    a.removeTradeables("other", evenMoreTradeables);
    
    tradeables.put("other", new LinkedList<ITradeable>());
    
    assertEquals(a.getAllGoods(), tradeables);
    
    // test clear function
    
    a.clear();
    
    assertTrue(a.getMoney() == 0.0); 
    
    assertEquals(a.getAllGoods(), new HashMap<String, List<ITradeable>>()); 
    
  }
  
  
}