package brown.market.preset;

import brown.rules.IActivityRule;
import brown.rules.IAllocationRule;
import brown.rules.IInformationRevelationPolicy;
import brown.rules.IInnerTC;
import brown.rules.IOuterTC;
import brown.rules.IPaymentRule;
import brown.rules.IQueryRule;

/**
 * provides a clean format for creating sets of rules for markets.
 */
public abstract class AbsMarketPreset {
  
  public IAllocationRule aRule; 
  public IPaymentRule pRule; 
  public IQueryRule qRule;
  public IActivityRule actRule; 
  public IInformationRevelationPolicy infoPolicy;
  public IInnerTC innerTCondition; 
  public IOuterTC outerTCondition; 
  
  public AbsMarketPreset(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule, 
      IActivityRule oneShotActivity, IInformationRevelationPolicy infoPolicy, IInnerTC innerTCondition, 
      IOuterTC outerTCondition) {
    this.aRule = aRule; 
    this.pRule = pRule; 
    this.qRule = qRule; 
    this.actRule = oneShotActivity; 
    this.infoPolicy = infoPolicy; 
    this.innerTCondition = innerTCondition; 
    this.outerTCondition = outerTCondition; 
  }
}