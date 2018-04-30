package brown.setup.library;

import java.math.BigDecimal;

import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.messages.library.SimpleSealedReportMessage;
import brown.setup.ISetup;
import brown.value.generator.library.SpecValGenerator;
import brown.value.valuation.library.XORValuation;

import com.esotericsoftware.kryo.Kryo;

/**
 * Additional setup for VCG.
 * @author andrew
 */
public class VCGSetup implements ISetup{

  @Override
  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(XORValuation.class);        
    kryo.register(SpecValGenerator.class);    
    kryo.register(AuctionChannel.class);
    kryo.register(SimpleSealedReportMessage.class);   
//    kryo.register(Bids.class);
//    kryo.register(XORBid.class); 
//    kryo.register(MRMGlobalBidder.class); 
//    kryo.register(BigDecimal.class);
    //kryo.register(NaturalOrdering.class);
  } 
  
}