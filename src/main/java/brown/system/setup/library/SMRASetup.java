package brown.system.setup.library;

import brown.auction.value.distribution.AdditiveValuationDistribution;
import brown.auction.value.generator.NormalValGenerator;
import brown.auction.value.valuation.AdditiveValuation;
import brown.mechanism.bid.AuctionBid;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.channel.OpenOutcryChannel;
import brown.mechanism.channel.SealedBidChannel;
import brown.platform.messages.SMRAReportMessage;
import brown.platform.messages.SimpleSealedReportMessage;
import brown.system.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for SSSP.
 * @author andrew
 */
public class SMRASetup implements ISetup {

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
    kryo.register(SMRAReportMessage.class); 
    kryo.register(OpenOutcryChannel.class); 
  }
}