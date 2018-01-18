package brown.market.preset.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.rules.activityrules.library.OneShotActivity;
import brown.rules.allocationrules.library.LemonadeAllocation;
import brown.rules.irpolicies.library.LemonadeAnonymous;
import brown.rules.paymentrules.library.LemonadePayment;
import brown.rules.queryrules.library.LemonadeQuery;
import brown.rules.terminationconditions.library.OneShotTermination;
import brown.rules.terminationconditions.library.ThreeRoundTermination;

/**
 * test the lemonade rules. 
 * I
 * @author andrew
 *
 */
public class LemonadeRulesTest {
  
  @Test
  public void testLemonadeRules() {
    LemonadeRules lemonadeRules = new LemonadeRules();
    assertEquals(lemonadeRules.aRule, new LemonadeAllocation());
    assertEquals(lemonadeRules.pRule, new LemonadePayment());
    assertEquals(lemonadeRules.qRule, new LemonadeQuery());
    assertEquals(lemonadeRules.actRule, new OneShotActivity());
    assertEquals(lemonadeRules.infoPolicy, new LemonadeAnonymous());
    assertEquals(lemonadeRules.innerTCondition, new OneShotTermination());
    assertEquals(lemonadeRules.outerTCondition, new ThreeRoundTermination());
  }
}