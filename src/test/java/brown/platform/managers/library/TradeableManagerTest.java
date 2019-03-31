package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
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
    Class<?> tTypeClass = Class.forName("brown.platform.tradeable.library.SimpleTradeable");
    Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.class); 
    
    ITradeableManager tManager = new TradeableManager(); 
    tManager.createTradeables("test", tTypeCons, 5);
    tManager.createTradeables("testTwo", tTypeCons, 4);
    
    Set<ITradeable> expected = new HashSet<ITradeable>(); 
    for (int i = 0; i < 5; i++) {
      expected.add(new Tradeable(i, "default")); 
    }
    
    Set<ITradeable> expectedTwo = new HashSet<ITradeable>(); 
    for (int i = 0; i < 4; i++) {
      expectedTwo.add(new Tradeable(i + 5, "default")); 
    }
    
    assertEquals(new HashSet<ITradeable>(tManager.getTradeables("test")), expected); 
    assertEquals(new HashSet<ITradeable>(tManager.getTradeables("testTwo")), expectedTwo); 
    
    
    tManager.lock();
    
    tManager.createTradeables("testThree", tTypeCons, 3);
    assertEquals(tManager.getTradeables("testThree"), new LinkedList<ITradeable>()); 
  }
  
}
