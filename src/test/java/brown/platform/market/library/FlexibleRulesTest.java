package brown.platform.market.library;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.platform.market.IMarketRules;

public class FlexibleRulesTest {

  @Test
  public void testFlexibleRules() {
    
    IAllocationRule mockAllocationRule = mock(IAllocationRule.class); 
    IPaymentRule mockPaymentRule = mock(IPaymentRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    
    IMarketRules mRules = new FlexibleRules(mockAllocationRule, mockPaymentRule, mockQueryRule, mockActivityRule, mockIR, mocktCondition);
    
    assertEquals(mRules.getARule(), mockAllocationRule); 
    
    assertEquals(mRules.getPRule(), mockPaymentRule);
    assertEquals(mRules.getQRule(), mockQueryRule);
    assertEquals(mRules.getActRule(), mockActivityRule);
    assertEquals(mRules.getIRPolicy(), mockIR);
    assertEquals(mRules.getTerminationCondition(), mocktCondition);
    
  }
}
