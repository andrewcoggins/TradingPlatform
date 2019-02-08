package brown.user.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.platform.market.IMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IMarketConfig;
import brown.user.main.library.MarketConfig; 

public class MarketConfigTest {
  
  @Test
  public void testMarketConfigOne() {
    IAllocationRule mockAllocationRule = mock(IAllocationRule.class); 
    IPaymentRule mockPaymentRule = mock(IPaymentRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    
    IMarketRules mRules = new FlexibleRules(mockAllocationRule, mockPaymentRule, mockQueryRule, mockActivityRule, mockIR, mocktCondition); 
    Map<String, Integer> numTradeablesMap = new HashMap<String, Integer>(); 
    numTradeablesMap.put("default", 1); 
    
    IMarketConfig mConfig = new MarketConfig(mRules, numTradeablesMap); 
    assertEquals(mConfig.getRules(), mRules); 
    assertEquals(mConfig.getNumTradeablesMap(), numTradeablesMap); 
    
  }
  
  @Test
  public void testMarketConfigTwo() {
    IAllocationRule mockAllocationRule = mock(IAllocationRule.class); 
    IPaymentRule mockPaymentRule = mock(IPaymentRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    
    IMarketRules mRules = new FlexibleRules(mockAllocationRule, mockPaymentRule, mockQueryRule, mockActivityRule, mockIR, mocktCondition); 
    Map<String, Integer> numTradeablesMap = new HashMap<String, Integer>(); 
    numTradeablesMap.put("default", 1); 
    
    Map<String, List<String>> mustInclude = new HashMap<String,List<String>>(); 
    List<String> aList = new LinkedList<String>(); 
    aList.add("A"); 
    mustInclude.put("default", aList); 
    
    IMarketConfig mConfig = new MarketConfig(mRules, numTradeablesMap, mustInclude); 
    assertEquals(mConfig.getRules(), mRules); 
    assertEquals(mConfig.getNumTradeablesMap(), numTradeablesMap); 
    assertEquals(mConfig.getMustInclude(), mustInclude); 
  }
  
}
