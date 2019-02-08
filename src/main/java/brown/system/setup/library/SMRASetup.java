package brown.system.setup.library;

import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.auction.value.valuation.library.AdditiveValuation;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.channel.library.OneSidedChannel;
import brown.communication.messages.library.SMRAReportMessage;
import brown.communication.messages.library.SimpleSealedReportMessage;
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
    kryo.register(OneSidedBidBundle.class);    
    kryo.register(AdditiveValuationDistribution.class);
    kryo.register(AdditiveValuation.class);        
    kryo.register(NormalValGenerator.class);    
    kryo.register(OneSidedChannel.class);
    kryo.register(SimpleSealedReportMessage.class);  
    kryo.register(SMRAReportMessage.class);  
  }
}