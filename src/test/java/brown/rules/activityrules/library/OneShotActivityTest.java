package brown.rules.activityrules.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.library.InternalState;
import brown.messages.library.TradeMessage;
import brown.tradeable.library.Tradeable;

/**
 * tests the one shot activity rule. 
 * C
 * @author andrew
 *
 */
public class OneShotActivityTest {
  
  @Test
  public void testOneShotActivity() {
    Set<Tradeable> allTradeables = new HashSet<Tradeable>();
    for(int i = 0; i < 3; i++) {
     allTradeables.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allTradeables);
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    aMap.put(new Tradeable(0), new MarketState(0, 1.0));
    SimpleBidBundle s = new SimpleBidBundle(aMap);
    TradeMessage aBid = new TradeMessage(1, s, 1, 1);
    OneShotActivity shot = new OneShotActivity();
    //is initially acceptable because doesn't contain bid. 
    shot.isAcceptable(state, aBid);
    assertTrue(state.getAcceptable());
    //now add the bid, it isn't acceptable
    state.addBid(aBid);
    shot.isAcceptable(state, aBid);
    assertTrue(!state.getAcceptable());
    //admittedly this interface is a little weird. I would
    //consider changing it but right now I appreciate the 
    //uniformity of the current rules interface. 
  }
}