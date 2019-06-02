package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import brown.platform.item.library.Item;
import brown.platform.managers.IItemManager;

public class ItemManagerTest {

  @Test
  public void testTradeableManager() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {


    IItemManager tManager = new ItemManager(); 
    tManager.createItems("test", 5);
    tManager.createItems("testTwo", 4);


    assertEquals(tManager.getItems("test") , new Item("test", 5)); 
    assertEquals(tManager.getItems("testTwo"), new Item("testTwo", 4));  
    
    
    tManager.lock();
    
  }
  
}
