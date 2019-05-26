package brown.platform.managers.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManagerTest {

  @Test
  public void testEndowmentManager() {

    IEndowmentManager m = new EndowmentManager();
    Map<String, Integer> included = new HashMap<String, Integer>();
    included.put("default", 5);

    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("default", 5));
    ICart aCart = new Cart(items); 
    
    m.createEndowment("e", included, 1, aCart, 90.0);

    IInitialEndowment testEndowment = new InitialEndowment(90.0, aCart);

    assertEquals(m.getEndowment(), testEndowment);

  }
}
