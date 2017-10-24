package brown.markets.presets;

import brown.marketinternalstates.MarketInternalState;
import brown.rules.activityrules.ActivityRule;
import brown.rules.allocationrules.AllocationRule;
import brown.rules.irpolicies.InformationRevelationPolicy;
import brown.rules.paymentrules.PaymentRule;
import brown.rules.queryrules.QueryRule;
import brown.rules.terminationconditions.TerminationCondition;

/*
 * provides a clean format for creating sets of rules for markets.
 */
public abstract class MarketPreset {
  
  public AllocationRule aRule; 
  public PaymentRule pRule; 
  public QueryRule qRule;
  public ActivityRule actRule; 
  public InformationRevelationPolicy infoPolicy;
  public TerminationCondition tCondition; 
  public MarketInternalState startingState;
  
}