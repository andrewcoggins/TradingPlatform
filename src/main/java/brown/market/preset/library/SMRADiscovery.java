package brown.market.preset.library; 

import java.util.Map;

import brown.market.preset.AbsMarketPreset;
import brown.rules.library.NoAllocation;
import brown.rules.library.NoPayment;
import brown.rules.library.NoRecordKeeping;
import brown.rules.library.OneGrouping;
import brown.rules.library.RevealedPreferenceActivity;
import brown.rules.library.SMRAPolicy;
import brown.rules.library.SMRAQuery;
import brown.rules.library.SomeBidsTermination;
import brown.rules.library.XRoundTermination;
import brown.tradeable.ITradeable;

/**
 * rules for an SMRA price discovery auction, where no allocations or payments are made.  
 * @author acoggins
 *
 */
public class SMRADiscovery extends AbsMarketPreset {

  private Map<ITradeable, Double> base; 
  private Map<ITradeable, Double> increment; 
  
  public SMRADiscovery(Map<ITradeable, Double> base, Map<ITradeable, Double> increment) {
    super(new NoAllocation(), 
        new NoPayment(), 
        new SMRAQuery(), 
        new OneGrouping(),
        new RevealedPreferenceActivity(base, increment), 
        new SMRAPolicy(), 
        new SomeBidsTermination(),
        new XRoundTermination(1), 
        new NoRecordKeeping());
    this.base = base; 
    this.increment = increment; 
  }

  @Override
  public AbsMarketPreset copy() {
    return new SMRADiscovery(this.base, this.increment);
  } 
   
}