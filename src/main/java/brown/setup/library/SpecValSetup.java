package brown.setup.library;


import brown.bid.library.AuctionBid;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.messages.library.SpecValValuationMessage;
import brown.setup.ISetup;

import com.esotericsoftware.kryo.Kryo;

public class SpecValSetup implements ISetup{

  public void setup(Kryo kryo) {
    Startup.start(kryo);
    kryo.register(AuctionBidBundle.class);
    kryo.register(AuctionBid.class);    
    kryo.register(AuctionChannel.class);
    kryo.register(SpecValValuationMessage.class);
    kryo.register(brown.channels.library.QueryChannel.class);
    kryo.register(brown.bidbundle.library.QueryBundle.class);
    kryo.register(brown.bid.library.QueryBid.class);  
    kryo.register(brown.messages.library.SpecValValuationReport.class); 
    kryo.register(brown.channels.library.OpenOutcryChannel.class);
    kryo.register(brown.messages.library.CombinatorialClockReport.class);    
    
//    kryo.register(SpecValDistV2.class);
//    kryo.register(SpecValGenV2.class);    
//    kryo.register(SpecValValuation.class);
//    kryo.register(SimpleSealedReportMessage.class);   
//    kryo.register(MultiRegionModel.class);
//    kryo.register(MRVMLocalBidderSetup.Builder.class);    
//    kryo.register(MRVMRegionalBidderSetup.Builder.class);
//    kryo.register(MRVMNationalBidderSetup.Builder.class);
//    kryo.register(MRVMWorldSetup.MRVMWorldSetupBuilder.class);    
//    kryo.register(MRVMWorldSetup.BandSetup.class);    
//    kryo.register(MRVMWorldSetup.RegionSetup.class);     
//    kryo.register(MRVMBidder.class);
//    kryo.register(DoubleInterval.class);
//    kryo.register(IntegerInterval.class);
//    kryo.register(java.math.BigDecimal.class);    
//    kryo.register(org.jgrapht.graph.SimpleGraph.class);
//    kryo.register(org.jgrapht.graph.ClassBasedEdgeFactory.class);    
//    kryo.register(java.lang.Class.class);    
//    kryo.register(org.jgrapht.graph.DefaultEdge.class);    
//    kryo.register(java.util.LinkedHashMap.class);    
//    kryo.register(org.jgrapht.util.ArrayUnenforcedSet.class);    
//    kryo.register(org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier.class);    
//    kryo.register(java.util.Random.class);    
//    kryo.register(java.util.concurrent.atomic.AtomicLong.class);    
//    kryo.register(org.spectrumauctions.sats.core.model.mrvm.MRVMWorld.class);   
//    kryo.register(org.spectrumauctions.sats.core.model.mrvm.MRVMBand.class);
//    kryo.register(org.spectrumauctions.sats.core.model.mrvm.MRVMLicense.class);    
//    kryo.register(org.spectrumauctions.sats.core.model.mrvm.MRVMRegionsMap.class);    
//    kryo.register(org.jgrapht.graph.UnmodifiableUndirectedGraph.class);    
//    kryo.register(org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidder.class);    
//    try {
//      kryo.register(Class.forName("org.jgrapht.graph.AbstractBaseGraph$ArrayListFactory"));   
//      kryo.register(Class.forName("org.jgrapht.graph.AbstractBaseGraph$UndirectedSpecifics"));   
//      kryo.register(Class.forName("org.jgrapht.graph.AbstractBaseGraph$UndirectedEdgeContainer"));         
//      kryo.register(Class.forName("com.google.common.collect.RegularImmutableMap"));
//      kryo.register(Class.forName("org.spectrumauctions.sats.core.model.mrvm.MRVMRegionsMap$Region"));
//      kryo.register(Class.forName("com.google.common.collect.NaturalOrdering"));
//    } catch (ClassNotFoundException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
  } 
}
