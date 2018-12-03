package brown.platform.market.library; 


 import brown.auction.rules.IActivityRule;  
import brown.auction.rules.IAllocationRule; 
import brown.auction.rules.IGroupingRule; 
import brown.auction.rules.IInformationRevelationPolicy;  
import brown.auction.rules.ITerminationCondition; 
import brown.auction.rules.IPaymentRule;  
import brown.auction.rules.IQueryRule;  
import brown.auction.rules.IRecordKeepingRule;  
 /**  
 * Describes all the rules for a particular market. 
 * These rules fully describe the behavior of the auction.  
 * @author acoggins 
 *  
 */ 
public abstract class AbsMarketRules {  
   public IAllocationRule aRule;  
  public IPaymentRule pRule;  
  public IQueryRule qRule;  
  public IActivityRule actRule;   
  public IInformationRevelationPolicy infoPolicy; 
  public ITerminationCondition innerTCondition;   
  public IGroupingRule gRule;   
  public IRecordKeepingRule rRule;  
    
  public AbsMarketRules(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule, IGroupingRule gRule, 
                        IActivityRule oneShotActivity, IInformationRevelationPolicy infoPolicy, ITerminationCondition innerTCondition, IRecordKeepingRule rRule) {  
    this.aRule = aRule;   
    this.pRule = pRule;   
    this.qRule = qRule;   
    this.gRule = gRule; 
    this.actRule = oneShotActivity;   
    this.infoPolicy = infoPolicy;   
    this.innerTCondition = innerTCondition;   
    this.rRule = rRule; 
  } 
    
  /** 
   * Why does this have to exist?   
   * @return  
   */ 
  public abstract AbsMarketRules copy();  
    
  @Override 
  public String toString() {  
    return "AbsMarketRules [aRule=" + aRule + ", pRule=" + pRule + ", qRule=" 
        + qRule + ", actRule=" + actRule + ", infoPolicy=" + infoPolicy 
        + ", innerTCondition=" + innerTCondition + ", gRule=" + gRule 
        + ", rRule=" + rRule + "]"; 
  } 
} 