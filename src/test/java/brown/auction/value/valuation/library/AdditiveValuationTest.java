package brown.auction.value.valuation.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;

public class AdditiveValuationTest {
  
  @Test
  public void testAdditiveValuation() {
    Map<IItem, Double> tMap = new HashMap<IItem, Double>();
    IItem item = new Item("default"); 
    tMap.put(item, 1.0); 
    AdditiveValuation a = new AdditiveValuation(tMap); 
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(item); 
    ICart cart= new Cart(items); 
    assertTrue(a.getValuation(cart) == 1.0); 
  }

}
