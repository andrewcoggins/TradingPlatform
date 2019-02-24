package brown.platform.market.library;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.platform.market.IMarketBlock;
import brown.platform.market.IMarketRules;
import brown.platform.tradeable.ITradeable;

public class SimultaneousMarketTest {

  @Test
  public void testSimultaneousMarket() {
    List<IMarketRules> rules = new LinkedList<IMarketRules>(); 
    List<Map<String, List<ITradeable>>> marketTradeables = new LinkedList<Map<String, List<ITradeable>>>();
    IMarketBlock s = new SimultaneousMarket(rules, marketTradeables); 
    
    assertEquals(s.getMarkets(), rules); 
    assertEquals(s.getMarketTradeables(), marketTradeables); 
  }
}
