package brown.system.setup.library;


import brown.mechanism.bid.library.AuctionBid;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.platform.messages.library.SpecValValuationMessage;
import brown.system.setup.ISetup;

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
    kryo.register(SealedBidChannel.class);
    kryo.register(SpecValValuationMessage.class);
    kryo.register(brown.platform.messages.library.SpecValValuationReport.class); 
    kryo.register(brown.mechanism.channel.library.OpenOutcryChannel.class);
    kryo.register(brown.platform.messages.library.CombinatorialClockReport.class);    
    kryo.register(brown.platform.messages.library.SpecValWrapperMessage.class);    
  } 
}
