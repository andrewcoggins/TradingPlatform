package brown.system.setup.library;


import brown.mechanism.bid.AuctionBid;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.channel.SealedBidChannel;
import brown.platform.messages.SpecValValuationMessage;
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
    kryo.register(brown.platform.messages.SpecValValuationReport.class); 
    kryo.register(brown.mechanism.channel.OpenOutcryChannel.class);
    kryo.register(brown.platform.messages.CombinatorialClockReport.class);    
    kryo.register(brown.platform.messages.SpecValWrapperMessage.class);    
  } 
}
