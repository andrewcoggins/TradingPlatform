package brown.user.main.library;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

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
    List<String> tradeableNames = new LinkedList<String>(); 
    tradeableNames.add("default"); 

    IMarketConfig mConfig = new MarketConfig(mRules, tradeableNames); 
    assertEquals(mConfig.getRules(), mRules); 
    assertEquals(mConfig.getTradeableNames(), tradeableNames); 
    
  }
  
}
