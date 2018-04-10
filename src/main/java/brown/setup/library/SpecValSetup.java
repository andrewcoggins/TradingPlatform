package brown.setup.library;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLocalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLocalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMRegionalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMRegionalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorldSetup;
import org.spectrumauctions.sats.core.model.mrvm.MultiRegionModel;
import org.spectrumauctions.sats.core.util.random.DoubleInterval;
import org.spectrumauctions.sats.core.util.random.IntegerInterval;

import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.messages.library.SimpleSealedReportMessage;
import brown.setup.ISetup;
import brown.value.distribution.library.SpecValDistV2;
import brown.value.generator.library.SpecValGenV2;
import brown.value.valuation.library.SpecValValuation;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.Kryo;

import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class SpecValSetup implements ISetup{

  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(SpecValDistV2.class);
    kryo.register(SpecValGenV2.class);    
    kryo.register(SpecValValuation.class);
    kryo.register(AuctionChannel.class);
    kryo.register(SimpleSealedReportMessage.class);   
    kryo.register(MultiRegionModel.class);
    kryo.register(MRVMLocalBidderSetup.Builder.class);    
    kryo.register(MRVMRegionalBidderSetup.Builder.class);
    kryo.register(MRVMNationalBidderSetup.Builder.class);
    kryo.register(MRVMWorldSetup.MRVMWorldSetupBuilder.class);    
    kryo.register(MRVMWorldSetup.BandSetup.class);    
    kryo.register(MRVMWorldSetup.RegionSetup.class);     
    kryo.register(MRVMBidder.class);
    kryo.register(DoubleInterval.class);
    kryo.register(IntegerInterval.class);
  } 
}
