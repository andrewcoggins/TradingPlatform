package brown.market.preset.library; 

import brown.market.preset.AbsMarketPreset;
import brown.rules.IActivityRule;
import brown.rules.IAllocationRule;
import brown.rules.IGroupingRule;
import brown.rules.IInformationRevelationPolicy;
import brown.rules.IInnerTC;
import brown.rules.IOuterTC;
import brown.rules.IPaymentRule;
import brown.rules.IQueryRule;
import brown.rules.IRecordKeepingRule;

public class SimpleSMRADiscovery extends AbsMarketPreset {

  public SimpleSMRADiscovery() {
    super(null, 
        null, 
        null, 
        null, 
        null, 
        null, 
        null,
        null, 
        null);
  }

  @Override
  public AbsMarketPreset copy() {
    // TODO Auto-generated method stub
    return null;
  } 
   
}