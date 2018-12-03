package brown.platform.market.library; 

 import brown.auction.rules.*;  
 public class FlexibleRules extends AbsMarketRules {  
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