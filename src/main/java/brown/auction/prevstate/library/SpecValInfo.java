package brown.auction.prevstate.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;

import brown.auction.prevstate.PrevStateType;
import brown.auction.value.config.IValuationConfig;
import brown.auction.value.config.library.SpecValV2Config;
import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;

public class SpecValInfo extends PrevStateInfo {
  public Map<Integer,MRVMBidder> vals;
  private Map<Integer, MRVMLicense> idToLicense;
  public PrevStateType type;
  public final Double valueScale = 1E-6;
  
  public SpecValInfo() {
    this.vals = null;
    this.idToLicense = null;
    this.type = PrevStateType.SPECTRUM;
  }
  
  public SpecValInfo(Map<Integer,MRVMBidder> vals, Map<Integer,MRVMLicense> idToLicense) {
    this.vals = vals;
    this.idToLicense = idToLicense;
    this.type = PrevStateType.SPECTRUM;
  }

  public Map<ComplexTradeable, Double> queryValues(Integer agent, List<ComplexTradeable> queries){
    Map<ComplexTradeable, Double> toReturn = new HashMap<ComplexTradeable, Double>();
    for (ComplexTradeable t: queries){
      toReturn.put(t,querySingleValue(agent, t));
    }
    return(toReturn);    
  }
  
  public Double querySingleValue(Integer agent, ComplexTradeable query){
    List<SimpleTradeable> tradeables = query.flatten();
    Set<MRVMLicense> mrvmBundle = new HashSet<MRVMLicense>();
    for (SimpleTradeable t : tradeables){
      mrvmBundle.add(this.idToLicense.get(t.ID));
    }
    return (this.valueScale * this.vals.get(agent).calculateValue(new Bundle<MRVMLicense>(mrvmBundle)).doubleValue()); 
  }
  
  // Dont need this to do anything
  @Override
  public void combine(PrevStateInfo prevState) {
  }

  @Override
  public void initialize(IValuationConfig gconfig) {
   this.vals = ((SpecValV2Config) gconfig).agentToValue;
  }

  @Override
  public PrevStateType getType() {
    // TODO Auto-generated method stub
    return this.type;
  }
}
