package brown.value.valuation.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spectrumauctions.sats.core.bidlang.xor.SizeBasedUniqueRandomXOR;
import org.spectrumauctions.sats.core.bidlang.xor.XORValue;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.UnsupportedBiddingLanguageException;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;

import brown.logging.Logging;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.valuation.ISpecValValuation;
import brown.value.valuation.IValuation;

public class SpecValValuation implements ISpecValValuation{

  private final MRVMBidder valuation; 
  private Map<Integer, MRVMLicense> idToLicense;
  
  public SpecValValuation(){
    this.valuation = null;
    this.idToLicense = null;
  }
  
  public SpecValValuation(MRVMBidder bidder) {
    this.valuation = bidder;
    this.idToLicense = new HashMap<Integer, MRVMLicense>();
    for (MRVMLicense l : bidder.getWorld().getLicenses()){
      this.idToLicense.put((int) l.getId(), l);
    }
  }  
  
  // Dont use this, doubles don't describe valuations
  @Override
  public Double getValuation(ITradeable tradeable) {
    return 0.;
  }

  @Override
  public IValuation safeCopy() {
    return new SpecValValuation(this.valuation);
  }

  @Override
  public Double queryValue(ComplexTradeable bundle) {
    List<SimpleTradeable> tradeables = bundle.flatten();
    Set<MRVMLicense> mrvmBundle = new HashSet<MRVMLicense>();
    for (SimpleTradeable t : tradeables){
      mrvmBundle.add(this.idToLicense.get(t.ID));
    }
    return this.valuation.calculateValue(new Bundle<MRVMLicense>(mrvmBundle)).doubleValue();
  }

  @Override
  public Map<ComplexTradeable, Double> generateXORBids(int nBids, int meanSize,int stdev) {
    Map<ComplexTradeable, Double> toReturn = new HashMap<ComplexTradeable, Double>();
    
    SizeBasedUniqueRandomXOR<?> valueFunction;
    try {
      valueFunction = (SizeBasedUniqueRandomXOR) this.valuation.getValueFunction(SizeBasedUniqueRandomXOR.class);
      valueFunction.setDistribution(meanSize, stdev, nBids);
      // Do something with the generated bids
      Iterator<? extends XORValue<?>> xorBidIterator = valueFunction.iterator();
      while (xorBidIterator.hasNext()) {
          XORValue bid = xorBidIterator.next();
          Bundle<MRVMLicense> licenses = bid.getLicenses();
          Set<ITradeable> tradeables = new HashSet<ITradeable>();
          for (MRVMLicense license : licenses){
            tradeables.add(new SimpleTradeable((int) license.getId()));
          }        
          // Always just make ID 0?
          toReturn.put(new ComplexTradeable(0,tradeables), this.valuation.calculateValue(licenses).doubleValue());
      }
      return toReturn;
    } catch (UnsupportedBiddingLanguageException e) {
      Logging.log("Unsupported Bidding Language Exception");
      return toReturn;
    }
  }
  
  public IValuation generateSubset(int n, int mean, int stdev){
    return new SpecValValuationSubset(generateXORBids(n,mean,stdev));    
  }

}
