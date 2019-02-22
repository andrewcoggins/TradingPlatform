package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.managers.IEndowmentManager;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;

public class EndowmentManagerTest {

   @Test
   public void testEndowmentManager() {
     
     IEndowmentManager m = new EndowmentManager(); 
     Map<String, Integer> included = new HashMap<String, Integer>(); 
     included.put("default", 5); 
     
     Map<String, List<ITradeable>> allTradeables = new HashMap<String, List<ITradeable>>(); 
     
    List<ITradeable> tList = new LinkedList<ITradeable>(); 
    
    for (int i = 0; i < 5; i++) {
      tList.add(new SimpleTradeable(i)); 
    }
    
    allTradeables.put("default", tList); 
    
    m.createEndowment("e", included, new HashMap<String, List<String>>(), 1, allTradeables, 90.0);
     
    IInitialEndowment testEndowment = new InitialEndowment(90.0, allTradeables); 
    
    assertEquals(m.getEndowment(), testEndowment); 
    
   }
}
