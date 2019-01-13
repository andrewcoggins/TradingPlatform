package brown.platform.input.config;

import java.util.List;
import java.util.Map;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;

/**
 * config for specifying markets. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IMarketConfig extends IInputConfig {
  
  /**
   * get allocation rule
   * @return
   */
  public  IAllocationRule getARule(); 
  
  /**
   * get payment rule
   * @return
   */
  public  IPaymentRule getPRule(); 
  
  /**
   * get query rule. 
   * @return
   */
  public  IQueryRule getQRule(); 
  
  /**
   * get activity rule. 
   * @return
   */
  public  IActivityRule getActRule(); 
  
  /**
   * get IR policy. 
   * @return
   */
  public  IInformationRevelationPolicy getIRPolicy(); 
  
  /**
   * get termination condition
   * @return
   */
  public  ITerminationCondition getTCondition();
  
  /**
   * get num tradeables map. 
   * @return
   */
  public  Map<String, Integer> getNumTradeablesMap(); 
  
  /**
   * get mustInclude information. 
   * @return
   */
  public  Map<String, List<String>> getMustInclude();
}