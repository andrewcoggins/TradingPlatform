package brown.system.setup.library;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.distribution.library.XORValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.auction.value.valuation.library.XORValuation;
import brown.mechanism.bid.library.OneSidedBidBundle;
import brown.mechanism.channel.library.OneSidedChannel;
import brown.platform.messages.library.SimpleSealedReportMessage;
import brown.platform.messages.library.StringMessage;
import brown.system.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for SSSP.
 * @author andrew
 */
public class SimpleSetup implements ISetup {

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(OneSidedBidBundle.class);    
    kryo.register(AdditiveValuationDistribution.class);
    kryo.register(AdditiveValuation.class);        
    kryo.register(NormalValGenerator.class);    
    kryo.register(OneSidedChannel.class);
    kryo.register(SimpleSealedReportMessage.class);  
    kryo.register(XORValuationDistribution.class); 
    kryo.register(XORValuation.class); 
    kryo.register(StringMessage.class); 
  }
}