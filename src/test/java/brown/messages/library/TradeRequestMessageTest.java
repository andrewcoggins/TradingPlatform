package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.channels.MechanismType;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.channel.SealedBidChannel;
import brown.mechanism.tradeable.MultiTradeable;
import brown.platform.accounting.Ledger;
import brown.platform.messages.TradeRequestMessage;
import brown.todeprecate.PaymentType;

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
        new SealedBidChannel(new Integer(0), new Ledger(0), PaymentType.SecondPrice,
            MechanismType.SealedBid, sb, 0),
        MechanismType.SealedBid); 
    
    assertEquals(MechanismType.SealedBid, trm.MECHANISM); 
    assertEquals(trm.MARKET,
        new SealedBidChannel(new Integer(0), new Ledger(0), PaymentType.SecondPrice,
            MechanismType.SealedBid, sb, 0)); 
  }
}