package brown.setup.library;

import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.agent.library.SSSPChannel;
import brown.messages.library.SSSPReportMessage;
import brown.setup.ISetup;
import brown.value.distribution.library.AdditiveValuationDistribution;
import brown.value.generator.library.NormalValGenerator;
import brown.value.valuation.library.AdditiveValuation;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for SSSP.
 * @author andrew
 */
public class SSSPSetup implements ISetup{

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(AdditiveValuationDistribution.class);
    kryo.register(AdditiveValuation.class);        
    kryo.register(NormalValGenerator.class);    
    kryo.register(SSSPChannel.class);
    kryo.register(SSSPReportMessage.class);    
  } 
  
}