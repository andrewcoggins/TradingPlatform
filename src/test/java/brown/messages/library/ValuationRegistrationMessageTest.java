package brown.messages.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.tradeable.library.Tradeable;
import brown.value.valuable.library.Value;
import brown.value.valuationrepresentation.library.ComplexValuation;
import brown.value.valuationrepresentation.library.SimpleValuation;

/**
 * tests the valuation registration message class. 
 * I
 * @author andrew
 *
 */
public class ValuationRegistrationMessageTest {
  
  @Test
  public void testValuationRegistrationMessage() {
    // simple good case.
    Map<Tradeable, Value> vals = new HashMap<Tradeable, Value>(); 
    vals.put(new Tradeable(0), new Value(0.0));
    ValuationRegistrationMessage personal = 
        new ValuationRegistrationMessage(0, new SimpleValuation(vals)); 
    assertEquals(personal.getValuation(), new SimpleValuation(vals));
    //complex good case.
    Map<Set<Tradeable>, Value> valsTwo = new HashMap<Set<Tradeable>, Value>();
    Set<Tradeable> tr = new HashSet<Tradeable>(); 
    tr.add(new Tradeable(1)); 
    valsTwo.put(tr, new Value(1.0));
    ValuationRegistrationMessage personalCom = new ValuationRegistrationMessage(0, 
        new ComplexValuation(valsTwo));
    assertEquals(personalCom.getValuation(), new ComplexValuation(valsTwo));
    
    // case with an additive distribution.
    
  }
}