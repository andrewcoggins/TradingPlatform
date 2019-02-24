package brown.platform.market;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;

public interface IMarketRules {
  
  public IAllocationRule getARule() ;
  
  public IPaymentRule getPRule(); 
  
  public IQueryRule getQRule(); 
  
  public IActivityRule getActRule(); 
  
  public IInformationRevelationPolicy getIRPolicy(); 
  
  public ITerminationCondition getTerminationCondition(); 
  
  
}