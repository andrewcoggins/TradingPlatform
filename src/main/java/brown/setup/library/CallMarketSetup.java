package brown.setup.library;

import com.esotericsoftware.kryo.Kryo;

import brown.bid.library.BidDirection;
import brown.bid.library.CancelBid;
import brown.bid.library.TwoSidedBid;
import brown.bidbundle.library.CancelBundle;
import brown.bidbundle.library.TwoSidedBidBundle;
import brown.channels.library.CallMarketChannel;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;
import brown.messages.library.CallMarketReportMessage;
import brown.messages.library.PredictionMarketReport;
import brown.messages.library.PredictionMarketValuationMessage;
import brown.setup.ISetup;

public class CallMarketSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(PredictionMarketValuationMessage.class); 
    kryo.register(TwoSidedBidBundle.class);
    kryo.register(TwoSidedBid.class);    
    kryo.register(CallMarketChannel.class);
    kryo.register(OrderBook.class);    
    kryo.register(java.util.PriorityQueue.class);
    kryo.register(BidDirection.class);    
    kryo.register(CallMarketReportMessage.class);
    kryo.register(BuyOrder.class);
    kryo.register(SellOrder.class);
    kryo.register(PredictionMarketReport.class);
    kryo.register(CancelBundle.class);
    kryo.register(CancelBid.class);
  } 
  
}