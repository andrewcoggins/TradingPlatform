package brown.value.generator.library;


import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.junit.Test;

import brown.tradeable.library.Good;
import brown.value.valuable.library.Value;


/**
 * tests size dependent generator.
 * C
 * @author andrew
 *
 */
public class SizeDependentGeneratorTest {
  
  @Test
  public void testSdg() {
    // basic functionality
    Function<Integer, Double> aFunction = x -> (double) x;
    SizeDependentGenerator test = new SizeDependentGenerator(aFunction);
    Good good = new Good();
    assertEquals(new Value(1.0), test.makeValuation(good));
    // a single good 
    Function<Integer, Double> anotherFunction = x -> (double) 2 * x; 
    SizeDependentGenerator testTwo = new SizeDependentGenerator(anotherFunction);
    assertEquals(new Value(2.0), testTwo.makeValuation(good)); 
    // test value scale feature
    SizeDependentGenerator testThree = new SizeDependentGenerator(anotherFunction, 2.0);
    assertEquals(new Value(4.0), testThree.makeValuation(good)); 
    // test for a set of goods.
    Set<Good> goodSet = new HashSet<Good>(); 
    for (int i = 0; i < 10; i++) {
      goodSet.add(new Good(i)); 
    }
    Function<Integer, Double> thirdFunction = x -> (double) x * x; 
    SizeDependentGenerator testFour = new SizeDependentGenerator(thirdFunction);
    assertEquals(new Value(100.0), testFour.makeValuation(goodSet));
    // set with set and value function
    SizeDependentGenerator testFive = new SizeDependentGenerator(thirdFunction, 0.5);
    assertEquals(new Value(50.0), testFive.makeValuation(goodSet));
  }
}