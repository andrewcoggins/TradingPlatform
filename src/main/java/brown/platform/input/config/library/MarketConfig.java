package brown.platform.input.config.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.platform.input.config.IMarketConfig;

public class MarketConfig implements IMarketConfig {
  
  private IAllocationRule aRule; 
  private IPaymentRule pRule; 
  private IQueryRule qRule; 
  private IActivityRule actRule; 
  private IInformationRevelationPolicy IRPolicy; 
  private ITerminationCondition tCondition;
  private Map<String, Integer> numTradeablesMap; 
  private Map<String, List<String>> mustInclude; 
 
  public MarketConfig(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule,
      IActivityRule actRule, IInformationRevelationPolicy IRPolicy, ITerminationCondition tCondition, 
      Map<String, Integer> numTradeablesMap, Map<String, List<String>> mustInclude) {
    this.aRule = aRule; 
    this.pRule = pRule; 
    this.qRule = qRule; 
    this.actRule = actRule; 
    this.IRPolicy = IRPolicy; 
    this.tCondition = tCondition; 
    this.numTradeablesMap = numTradeablesMap; 
    this.mustInclude = mustInclude;   
  }
  
  public MarketConfig(IAllocationRule aRule, IPaymentRule pRule, IQueryRule qRule,
      IActivityRule actRule, IInformationRevelationPolicy IRPolicy, ITerminationCondition tCondition, 
      Map<String, Integer> numTradeablesMap) {
    this.aRule = aRule; 
    this.pRule = pRule; 
    this.qRule = qRule; 
    this.actRule = actRule; 
    this.IRPolicy = IRPolicy; 
    this.tCondition = tCondition; 
    this.numTradeablesMap = numTradeablesMap; 
    this.mustInclude = new HashMap<String, List<String>>(); 
    
  }

  @Override
  public IAllocationRule getARule() {
    return this.aRule;
  }

  @Override
  public IPaymentRule getPRule() {
    return this.pRule;
  }

  @Override
  public IQueryRule getQRule() {
    return this.qRule;
  }

  @Override
  public IActivityRule getActRule() {
    return this.actRule;
  }

  @Override
  public IInformationRevelationPolicy getIRPolicy() {   
    return this.IRPolicy;
  }

  @Override
  public ITerminationCondition getTCondition() {   
    return this.tCondition;
  }

  @Override
  public Map<String, Integer> getNumTradeablesMap() { 
    return this.numTradeablesMap;
  }

  @Override
  public Map<String, List<String>> getMustInclude() {
    return this.mustInclude;
  }
}