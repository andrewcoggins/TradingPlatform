package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.library.Good;
import brown.value.valuable.library.Value;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuation.library.BundleValuation;
import brown.value.valuationrepresentation.library.ComplexValuation;
import brown.value.valuationrepresentation.library.SimpleValuation;

/**
 * tests the valuation registration message class. 
 * C
 * @author andrew
 *
 */
public class ValuationRegistrationMessageTest {
  
  @Test
  public void testValuationRegistrationMessage() {
    // simple good case.
    Map<Good, Value> vals = new HashMap<Good, Value>(); 
    vals.put(new Good(0), new Value(0.0));
    ValuationRegistrationMessage personal = 
        new ValuationRegistrationMessage(0, new SimpleValuation(vals)); 
    assertEquals(personal.getValuation(), new SimpleValuation(vals));
    //complex good case.
    Map<Set<Good>, Value> valsTwo = new HashMap<Set<Good>, Value>();
    Set<Good> tr = new HashSet<Good>(); 
    tr.add(new Good(1)); 
    valsTwo.put(tr, new Value(1.0));
    ValuationRegistrationMessage personalCom = new ValuationRegistrationMessage(0, 
        new ComplexValuation(valsTwo));
    assertEquals(personalCom.getValuation(), new ComplexValuation(valsTwo));
    
    // case with an additive distribution.
    AdditiveValuation av = new AdditiveValuation(tr);
    BundleValuation bv = new BundleValuation(tr); 
    ValuationRegistrationMessage pubSimple =
        new ValuationRegistrationMessage(0, new SimpleValuation(vals), av); 
    ValuationRegistrationMessage pubComplex = 
        new ValuationRegistrationMessage(0, new ComplexValuation(valsTwo), bv);
    assertEquals(pubSimple.getDistribution(), av); 
    assertEquals(pubComplex.getDistribution(), bv);
  }
}