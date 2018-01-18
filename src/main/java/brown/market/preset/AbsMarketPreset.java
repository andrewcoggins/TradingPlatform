package brown.market.preset;

import brown.rules.activityrules.IActivityRule;
import brown.rules.allocationrules.IAllocationRule;
import brown.rules.irpolicies.IInformationRevelationPolicy;
import brown.rules.paymentrules.IPaymentRule;
import brown.rules.queryrules.IQueryRule;
import brown.rules.terminationconditions.IInnerTC;
import brown.rules.terminationconditions.IOuterTC;

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
      IActivityRule actRule, IInformationRevelationPolicy infoPolicy, IInnerTC innerTCondition, 
      IOuterTC outerTCondition) {
    this.aRule = aRule; 
    this.pRule = pRule; 
    this.qRule = qRule; 
    this.actRule = actRule; 
    this.infoPolicy = infoPolicy; 
    this.innerTCondition = innerTCondition; 
    this.outerTCondition = outerTCondition; 
  }
}