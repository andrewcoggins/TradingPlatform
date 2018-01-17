package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;

/**
 * tests the trade request message class. 
 * C
 * @author andrew
 *
 */
public class TradeRequestMessageTest {
  
  @Test
  public void testTradeRequestMessage() {
    
    Ledger l = new Ledger();
    Map<Tradeable, MarketState> bids = new HashMap<Tradeable, MarketState>();
    bids.put(new Tradeable(0), new MarketState(0, 1.0));
    SimpleBidBundle sb = new SimpleBidBundle(bids);
    TradeRequestMessage trm = new TradeRequestMessage(0, 
        new SimpleAgentChannel(new Integer(0), new Ledger(0), PaymentType.SecondPrice,
            MechanismType.SealedBid, sb, 0),
        MechanismType.SealedBid); 
    
    assertEquals(MechanismType.SealedBid, trm.MECHANISM); 
    assertEquals(trm.MARKET,
        new SimpleAgentChannel(new Integer(0), new Ledger(0), PaymentType.SecondPrice,
            MechanismType.SealedBid, sb, 0)); 
  }
}