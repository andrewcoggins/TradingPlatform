package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.library.Ledger;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.MultiTradeable;

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
    Map<MultiTradeable, MarketState> bids = new HashMap<MultiTradeable, MarketState>();
    bids.put(new MultiTradeable(0), new MarketState(0, 1.0));
    AuctionBidBundle sb = new AuctionBidBundle(bids);
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