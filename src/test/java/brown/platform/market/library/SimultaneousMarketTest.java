package brown.platform.market.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarketBlock;

public class SimultaneousMarketTest {

  @Test
  public void testSimultaneousMarket() {
    
    List<IFlexibleRules> rules = new LinkedList<IFlexibleRules>(); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(new Item("a", 1)); 
    items.add(new Item("b", 2)); 
    
    List<ICart> carts = new LinkedList<ICart>(); 
    
    carts.add(new Cart(items)); 
    
    IMarketBlock s = new SimultaneousMarket(rules, carts); 
    
    assertEquals(s.getMarkets(), rules); 
    assertEquals(s.getMarketTradeables(), carts); 
  }
}
