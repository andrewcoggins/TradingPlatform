package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.accounting.IAccount;

/**
 * Test for account class.
 * @author andrew
 *
 */
public class AccountTest {
  
  @Test
  public void testAccountInit() {
    Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    tList.add(new SimpleTradeable(0)); 
    tList.add(new SimpleTradeable(1)); 
    
    tradeables.put("default", tList);
    IAccount a = new Account(100, 90.0, tradeables);
    assertTrue(a.getID() == 100); 
    assertTrue(a.getMoney() == 90.0); 
    assertEquals(a.getAllGoods(), tradeables); 
    assertEquals(a.getGoods("default"), tradeables.get("default")); 
  }
  

  public static void main(String[] args) {
    AccountTest t = new AccountTest(); 
    t.testAccountInit(); 
  }
}