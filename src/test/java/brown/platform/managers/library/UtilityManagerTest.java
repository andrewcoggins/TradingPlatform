package brown.platform.managers.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.value.valuation.IGeneralValuation;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.library.Account;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IUtilityManager;

public class UtilityManagerTest {

  
  @Test
  public void testUtilityManager() {
    
    IUtilityManager utilManager = new UtilityManager(); 
    
    utilManager.addAgentRecord(0);
    utilManager.addAgentRecord(1);
    
    assertTrue(utilManager.getAgentRecords().keySet().size() == 2); 
    
    Map<Integer, IAccount> accounts = new HashMap<Integer, IAccount>(); 
    
    Map<Integer, IGeneralValuation> valuations = new HashMap<Integer, IGeneralValuation>(); 
    
    List<IItem> agentItems = new LinkedList<IItem>(); 
    agentItems.add(new Item("test", 0)); 
    
    List<IItem> agentItemsTwo = new LinkedList<IItem>(); 
    agentItemsTwo.add(new Item("test", 1)); 
    
    ICart agentCart = new Cart(agentItems); 
    ICart agentCartTwo = new Cart(agentItemsTwo); 
    
    IAccount acctOne = new Account(0, 100.0, agentCart); 
    IAccount acctTwo = new Account(0, 100.0, agentCartTwo); 
    
    
  }
  
  
}
