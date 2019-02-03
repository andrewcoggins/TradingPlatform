package brown.auction.value.generator.library;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class NormalValGeneratorTest {

  @Test
  public void testNormalValGenerator() {
    List<Double> params = new LinkedList<Double>(); 
    params.add(0.0); 
    params.add(1.0); 
    NormalValGenerator n = new NormalValGenerator(params);
    for (int i = 0; i < 1000; i++) {
      assertTrue(n.makeValuation() > 0.0); 
      assertTrue(n.makeValuation() < 10.0); 
    }
  }
  
  @Test
  public void testNormalValGeneratorTwo() {
    List<Double> params = new LinkedList<Double>(); 
    params.add(100.0); 
    params.add(1.0); 
    NormalValGenerator n = new NormalValGenerator(params);
    for (int i = 0; i < 1000; i++) {
      assertTrue(n.makeValuation() > 90.0); 
      assertTrue(n.makeValuation() < 110.0); 
    }
  }
  
  public static void main(String[] args) {
    NormalValGeneratorTest t = new NormalValGeneratorTest(); 
    t.testNormalValGenerator();
    t.testNormalValGeneratorTwo();
  }
}
