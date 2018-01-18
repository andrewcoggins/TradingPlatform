package brown.rules.paymentrules.library;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.library.CompleteState;
import brown.tradeable.library.MultiTradeable;

/**
 * simple second price payment rule test.
 * I
 * @author andrew
 *
 */
public class SimpleSecondPriceTest {
  
  @Test
  public void testSimpleSecondPrice() { 
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    SimpleBidBundle al = new SimpleBidBundle(aMap);
    CompleteState state = new CompleteState(0, aMap.keySet());
    state.setAllocation(al);
    
    SimpleSecondPrice sim = new SimpleSecondPrice();
  }
}