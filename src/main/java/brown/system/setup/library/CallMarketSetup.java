package brown.system.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.mechanism.bid.library.BidDirection;
import brown.mechanism.bid.library.TwoSidedBid;
import brown.mechanism.channel.library.TwoSidedChannel;
import brown.platform.messages.library.CallMarketReportMessage;
import brown.platform.messages.library.PredictionMarketReport;
import brown.platform.messages.library.PredictionMarketValuationMessage;
import brown.platform.twosided.BuyOrder;
import brown.platform.twosided.OrderBook;
import brown.platform.twosided.SellOrder;
import brown.system.setup.ISetup;

/**
 * additional kryo setup for call market auction.
 * @author acoggins
 *
 */
public class CallMarketSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(PredictionMarketValuationMessage.class); 
    kryo.register(TwoSidedBid.class);    
    kryo.register(TwoSidedChannel.class);
    kryo.register(OrderBook.class);    
    kryo.register(java.util.PriorityQueue.class);
    kryo.register(BidDirection.class);    
    kryo.register(CallMarketReportMessage.class);
    kryo.register(BuyOrder.class);
    kryo.register(SellOrder.class);
    kryo.register(PredictionMarketReport.class);
  } 
  
}