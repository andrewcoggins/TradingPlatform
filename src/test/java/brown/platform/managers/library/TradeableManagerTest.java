package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Test;

import brown.platform.managers.ITradeableManager;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

public class TradeableManagerTest {

  @Test
  public void testTradeableManager() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {


    ITradeableManager tManager = new TradeableManager(); 
    tManager.createTradeables("test", 5);
    tManager.createTradeables("testTwo", 4);
    
    Set<ITradeable> expected = new HashSet<ITradeable>(); 
    for (int i = 0; i < 5; i++) {
      expected.add(new Tradeable(i, "test")); 
    }
    
    Set<ITradeable> expectedTwo = new HashSet<ITradeable>(); 
    for (int i = 0; i < 4; i++) {
      expectedTwo.add(new Tradeable(i + 5, "testTwo")); 
    }
    
    assertEquals(new HashSet<ITradeable>(tManager.getTradeables("test")), expected); 
    assertEquals(new HashSet<ITradeable>(tManager.getTradeables("testTwo")), expectedTwo); 
    
    
    tManager.lock();
    
    tManager.createTradeables("testThree", 3);
    assertEquals(tManager.getTradeables("testThree"), new LinkedList<ITradeable>()); 
  }
  
}
