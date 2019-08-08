package brown.auction.value.generator.library;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.auction.value.generator.IValuationGenerator;

public class ConcreteValGeneratorTest {

  @Test
  public void testConcreteValGenerator() {
    
    List<Double> a = new LinkedList<Double>(); 
    a.add(4.0); 
    IValuationGenerator concreteValGenerator = new ConcreteValGenerator(a); 
    
    assertTrue(concreteValGenerator.makeValuation() == 4.0); 
  }
  
}
