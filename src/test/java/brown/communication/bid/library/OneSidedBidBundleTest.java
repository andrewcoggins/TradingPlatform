package brown.communication.bid.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.SingleItem;

public class OneSidedBidBundleTest {

  @Test
  public void testOneSidedBidBundle() {
    
    
    Map<ICart, Double> bundle = new HashMap<ICart, Double>(); 
    
    OneSidedBidBundle aBid = new OneSidedBidBundle(bundle); 
    
    assertEquals(aBid.getBids(), bundle); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    
    items.add(new SingleItem("default")); 
    
    bundle.put(new Cart(items), 99.9); 
    
    OneSidedBidBundle bidTwo = new OneSidedBidBundle(bundle); 
    
    assertEquals(bidTwo.getBids(), bundle); 
  }
}
