package brown.rules.activityrules.library;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.library.CompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.library.OneShotActivity;
import brown.tradeable.library.MultiTradeable;

/**
 * tests the one shot activity rule. 
 * C
 * @author andrew
 *
 */
public class OneShotActivityTest {
  
  @Test
  public void testOneShotActivity() {
    Set<MultiTradeable> allTradeables = new HashSet<MultiTradeable>();
    for(int i = 0; i < 3; i++) {
     allTradeables.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allTradeables);
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    aMap.put(new MultiTradeable(0), new MarketState(0, 1.0));
    AuctionBidBundle s = new AuctionBidBundle(aMap);
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