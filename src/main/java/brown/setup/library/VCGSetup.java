package brown.setup.library;

import java.math.BigDecimal;

import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.messages.library.SimpleSealedReportMessage;
import brown.setup.ISetup;
import brown.value.distribution.library.SpecValDistribution;
import brown.value.generator.library.SpecValGenerator;
import brown.value.valuation.library.XORValuation;
import ch.uzh.ifi.ce.mweiss.specval.bidlang.xor.XORBid;
import ch.uzh.ifi.ce.mweiss.specval.model.mrm.MRMGlobalBidder;
import ch.uzh.ifi.ce.mweiss.specvalopt.vcg.external.domain.Bids;

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
    kryo.register(SpecValDistribution.class);
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