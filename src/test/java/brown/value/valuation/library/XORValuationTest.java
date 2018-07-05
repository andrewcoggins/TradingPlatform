package brown.value.valuation.library; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.auction.value.valuation.XORValuation;
import brown.mechanism.tradeable.ComplexTradeable;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.SimpleTradeable;

/**
 * check that an XORValuation corrctly stores its parameters.
 * @author acoggins
 *
 */
public class XORValuationTest {
  
  @Test
  public void testXORValuation() {
    
    Set<ITradeable> goods = new HashSet<ITradeable>(); 
    goods.add(new SimpleTradeable(0));
    goods.add(new SimpleTradeable(1)); 
    goods.add(new SimpleTradeable(2)); 
    ComplexTradeable cOne = new ComplexTradeable(0, goods); 
    Set<ITradeable> goodsTwo = new HashSet<ITradeable>(); 
    goodsTwo.add(new SimpleTradeable(2));
    goodsTwo.add(new SimpleTradeable(3)); 
    goodsTwo.add(new SimpleTradeable(4)); 
    ComplexTradeable cTwo = new ComplexTradeable(0, goodsTwo); 
    Map<ComplexTradeable, Double> valueParams = new HashMap<ComplexTradeable, Double>(); 
    valueParams.put(cOne, 1.0); 
    valueParams.put(cTwo, 2.0); 
    
    XORValuation aVal = new XORValuation(valueParams); 
    assertTrue(aVal.getValuation(cOne) == 1.0); 
    assertTrue(aVal.getValuation(cTwo) == 2.0); 
    Set<ComplexTradeable> t = aVal.getTradeables(); 
    assertTrue(t.contains(cOne)); 
    assertTrue(t.contains(cTwo)); 
  }
}