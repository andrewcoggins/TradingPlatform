package brown.auction.preset;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IGroupingRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerTC;
import brown.auction.rules.IOuterTC;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.IRecordKeepingRule;

/**
 * Describes all the rules for a particular market.
 * These rules fully describe the behavior of the auction.
 * @author acoggins
 *
 */
public abstract class AbsMarketPreset {
  
  public IAllocationRule aRule; 
  public IPaymentRule pRule; 
  public IQueryRule qRule;
  public IActivityRule actRule; 
  public IInformationRevelationPolicy infoPolicy;
  public IInnerTC innerTCondition; 
  public IOuterTC outerTCondition;
  public IGroupingRule gRule; 
  public IRecordKeepingRule rRule;
  
  public AbsMarketPreset(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule, IGroupingRule gRule,
      IActivityRule oneShotActivity, IInformationRevelationPolicy infoPolicy, IInnerTC innerTCondition, 
      IOuterTC outerTCondition, IRecordKeepingRule rRule) {
    this.aRule = aRule; 
    this.pRule = pRule; 
    this.qRule = qRule; 
    this.gRule = gRule;
    this.actRule = oneShotActivity; 
    this.infoPolicy = infoPolicy; 
    this.innerTCondition = innerTCondition; 
    this.outerTCondition = outerTCondition; 
    this.rRule = rRule;
  }
  
  /**
   * Why does this have to exist? 
   * @return
   */
  public abstract AbsMarketPreset copy();
}