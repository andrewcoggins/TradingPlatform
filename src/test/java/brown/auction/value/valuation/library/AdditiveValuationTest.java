package brown.auction.value.valuation.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.communication.bid.ICart;
import brown.communication.bid.IItem;
import brown.communication.bid.ISingleItem;
import brown.communication.bid.library.Cart;
import brown.communication.bid.library.SingleItem;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

public class AdditiveValuationTest {
  
  @Test
  public void testAdditiveValuation() {
    ITradeable tradeable = new Tradeable(0, "default"); 
    Map<ISingleItem, Double> tMap = new HashMap<ISingleItem, Double>();
    ISingleItem item = new SingleItem(tradeable); 
    tMap.put(item, 1.0); 
    AdditiveValuation a = new AdditiveValuation(tMap); 
    List<IItem> items = new LinkedList<IItem>(); 
    items.add(item); 
    ICart cart= new Cart(items); 
    assertTrue(a.getValuation(cart) == 1.0); 
  }

}
