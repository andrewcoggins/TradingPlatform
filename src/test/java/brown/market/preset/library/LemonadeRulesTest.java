package brown.market.preset.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.auction.preset.LemonadeAnonRules;
import brown.auction.rules.library.LemonadeActivity;
import brown.auction.rules.library.LemonadeAllocation;
import brown.auction.rules.library.LemonadeAnonymous;
import brown.auction.rules.library.LemonadePayment;
import brown.auction.rules.library.LemonadeQuery;
import brown.auction.rules.library.OneShotTermination;
import brown.auction.rules.library.XRoundTermination;

/**
 * test the lemonade rules. 
 * I
 * @author andrew
 *
 */
public class LemonadeRulesTest {
  
  @Test
  public void testLemonadeRules() {
    LemonadeAnonRules lemonadeRules = new LemonadeAnonRules();
    assertEquals(lemonadeRules.aRule, new LemonadeAllocation());
    assertEquals(lemonadeRules.pRule, new LemonadePayment());
    assertEquals(lemonadeRules.qRule, new LemonadeQuery());
    assertEquals(lemonadeRules.actRule, new LemonadeActivity());
    assertEquals(lemonadeRules.infoPolicy, new LemonadeAnonymous());
    assertEquals(lemonadeRules.innerTCondition, new OneShotTermination());
    assertEquals(lemonadeRules.outerTCondition, new XRoundTermination());
  }
}