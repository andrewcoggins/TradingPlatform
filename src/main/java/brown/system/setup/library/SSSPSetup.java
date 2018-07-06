package brown.system.setup.library;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.mechanism.bid.library.AuctionBid;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.channel.library.SealedBidChannel;
import brown.platform.messages.library.SimpleSealedReportMessage;
import brown.system.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for SSSP.
 * @author andrew
 */
public class SSSPSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(AdditiveValuationDistribution.class);
    kryo.register(AdditiveValuation.class);        
    kryo.register(NormalValGenerator.class);    
    kryo.register(SealedBidChannel.class);
    kryo.register(SimpleSealedReportMessage.class);    
  }
}