package brown.platform.market.library; 

 import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.platform.market.IFlexibleRules;  
 
/**
 * Used to group a set of market rules together. 
 * @author andrewcoggins
 *
 */
 public class FlexibleRules extends AbsMarketRules implements IFlexibleRules {  
   
   /**
    * Consists of one of each of the market rules. 
    * @param aRule
    * @param pRule
    * @param qRule
    * @param actRule
    * @param irPolicy
    * @param innerIRPolicy
    * @param tCondition
    */
    public FlexibleRules(IAllocationRule aRule, 
                         IPaymentRule pRule,  
                         IQueryRule qRule,  
                         IActivityRule actRule, 
                         IInformationRevelationPolicy irPolicy, 
                         IInnerIRPolicy innerIRPolicy, 
                         ITerminationCondition tCondition  
                         ) {  
        super(aRule, pRule, qRule, actRule, irPolicy, innerIRPolicy, tCondition);  
    } 
    
     public AbsMarketRules copy() { 
        return new FlexibleRules(aRule, pRule, qRule, actRule, infoPolicy, innerIRPolicy, tCondition);  
    } 
}