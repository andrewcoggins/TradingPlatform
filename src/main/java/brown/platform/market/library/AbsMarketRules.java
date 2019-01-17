package brown.platform.market.library; 

import brown.auction.rules.IActivityRule;  
import brown.auction.rules.IAllocationRule; 
import brown.auction.rules.IInformationRevelationPolicy;  
import brown.auction.rules.ITerminationCondition;
import brown.platform.market.IMarketRules;
import brown.auction.rules.IPaymentRule;  
import brown.auction.rules.IQueryRule;

/**  
* Describes all the rules for a particular market.
* 
* @author acoggins 
*/

public abstract class AbsMarketRules implements IMarketRules {
 
  public IAllocationRule aRule;  
  public IPaymentRule pRule;  
  public IQueryRule qRule;  
  public IActivityRule actRule;   
  public IInformationRevelationPolicy infoPolicy; 
  public ITerminationCondition tCondition;   
    
  public AbsMarketRules(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule, 
                        IActivityRule oneShotActivity, IInformationRevelationPolicy infoPolicy, ITerminationCondition tCondition) {  
    this.aRule = aRule;   
    this.pRule = pRule;   
    this.qRule = qRule;    
    this.actRule = oneShotActivity;   
    this.infoPolicy = infoPolicy;   
    this.tCondition = tCondition;   
  } 

  @Override 
  public String toString() {  
    return "AbsMarketRules [aRule=" + aRule + ", pRule=" + pRule + ", qRule=" 
        + qRule + ", actRule=" + actRule + ", infoPolicy=" + infoPolicy 
        + ", innerTCondition=" + tCondition + "]"; 
  }
 
} 
