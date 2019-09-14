package brown.platform.market;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;

/**
 * IMarketRules encapsulates the rules that define a Market. 
 * 
 * @author andrewcoggins
 *
 */
public interface IMarketRules {
  
  /**
   * get AllocationRule
   * @return
   */
  public IAllocationRule getARule();
  
  /**
   * get PaymentRule
   * @return
   */
  public IPaymentRule getPRule(); 
  
  /**
   * get QueryRule
   * @return
   */
  public IQueryRule getQRule(); 
  
  /**
   * get ActivityRule
   * @return
   */
  public IActivityRule getActRule(); 
  
  /**
   * get InformationRevelationPolicy
   * @return
   */
  public IInformationRevelationPolicy getIRPolicy(); 
  
  /**
   * get InnerIRPolicy
   * @return
   */
  public IInnerIRPolicy getInnerIRPolicy(); 
  
  /**
   * get TerminationCondition
   * @return
   */
  public ITerminationCondition getTerminationCondition(); 

}
