package brown.communication.bid.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.communication.bid.ICart;
import brown.communication.bid.IItem;
import brown.platform.tradeable.library.Tradeable;

public class OneSidedBidBundleTest {

  @Test
  public void testOneSidedBidBundle() {
    
    
    Map<ICart, Double> bundle = new HashMap<ICart, Double>(); 
    
    OneSidedBidBundle aBid = new OneSidedBidBundle(bundle); 
    
    assertEquals(aBid.getBids(), bundle); 
    
    List<IItem> items = new LinkedList<IItem>(); 
    
    items.add(new SingleItem(new Tradeable(0, "default"))); 
    
    bundle.put(new Cart(items), 99.9); 
    
    OneSidedBidBundle bidTwo = new OneSidedBidBundle(bundle); 
    
    assertEquals(bidTwo.getBids(), bundle); 
  }
}
