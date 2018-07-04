package brown.setup.library;


import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.messages.library.SpecValValuationMessage;
import brown.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

/**
 * additional setup for specval auction.
 * @author acoggins
 *
 */
public class SpecValSetup implements ISetup {

  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(AuctionChannel.class);
    kryo.register(SpecValValuationMessage.class);
    kryo.register(brown.messages.library.SpecValValuationReport.class); 
    kryo.register(brown.channels.library.OpenOutcryChannel.class);
    kryo.register(brown.messages.library.CombinatorialClockReport.class);    
    kryo.register(brown.messages.library.SpecValWrapperMessage.class);    
  } 
}
