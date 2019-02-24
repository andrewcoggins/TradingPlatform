package brown.platform.market.library; 

 import brown.auction.rules.*;
import brown.platform.market.IMarketRules;  
 
 public class FlexibleRules extends AbsMarketRules implements IMarketRules {  
   
    public FlexibleRules(IAllocationRule aRule, 
                         IPaymentRule pRule,  
                         IQueryRule qRule,  
                         IActivityRule actRule, 
                         IInformationRevelationPolicy irPolicy, 
                         ITerminationCondition tCondition  
                         ) {  
        super(aRule, pRule, qRule, actRule, irPolicy, tCondition);  
    } 
    
     public AbsMarketRules copy() { 
        return new FlexibleRules(aRule, pRule, qRule, actRule, infoPolicy, tCondition);  
    } 
}