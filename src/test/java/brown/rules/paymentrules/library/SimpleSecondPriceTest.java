package brown.rules.paymentrules.library;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.library.InternalState;
import brown.tradeable.library.Tradeable;

/**
 * simple second price payment rule test.
 * I
 * @author andrew
 *
 */
public class SimpleSecondPriceTest {
  
  @Test
  public void testSimpleSecondPrice() { 
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    SimpleBidBundle al = new SimpleBidBundle(aMap);
    InternalState state = new InternalState(0, aMap.keySet());
    state.setAllocation(al);
    
    SimpleSecondPrice sim = new SimpleSecondPrice();
  }
}