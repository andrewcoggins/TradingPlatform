package brown.communication.bid.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;

public class OneSidedBidBundleTest {

  @Test
  public void testOneSidedBidBundle() {
    
    Map<ITradeable, Double> bundle = new HashMap<ITradeable, Double>(); 
    
    OneSidedBidBundle aBid = new OneSidedBidBundle(bundle); 
    
    assertEquals(aBid.bids, bundle); 
    
    bundle.put(new SimpleTradeable(0), 99.9); 
    
    OneSidedBidBundle bidTwo = new OneSidedBidBundle(bundle); 
    
    assertEquals(bidTwo.bids, bundle); 
  }
}
