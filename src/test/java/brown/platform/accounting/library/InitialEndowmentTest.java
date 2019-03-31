package brown.platform.accounting.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

public class InitialEndowmentTest {

    @Test
    public void testInitialEndowment() {
       
      Map<String, List<ITradeable>> tradeables = new HashMap<String, List<ITradeable>>(); 
      
      List<ITradeable> someTradeables = new LinkedList<ITradeable>(); 
      
      someTradeables.add(new Tradeable(0, "default")); 
      
      someTradeables.add(new Tradeable(1, "default")); 
      
      tradeables.put("default", someTradeables); 
      
      IInitialEndowment anEndowment = new InitialEndowment(100.0, tradeables); 
      
      assertTrue(anEndowment.getMoney() == 100.0); 
      assertTrue(anEndowment.getMoney() != 50.0); 
      
      assertEquals(anEndowment.getGoods(), tradeables); 
      
      Map<String, List<ITradeable>> moreTradeables = new HashMap<String, List<ITradeable>>(); 
      
      assertTrue(!(anEndowment.getGoods().equals(moreTradeables))); 
      
    }
    
}
