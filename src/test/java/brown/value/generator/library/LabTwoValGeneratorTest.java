package brown.value.generator.library;

import static org.junit.Assert.assertTrue;



import org.junit.Test;

import brown.auction.value.generator.library.LabTwoValGenerator;

/**
 * tests the lab two value generator, and that it fits the specification of 
 * the CDF F(X) = 2x - x^2 over [0, 1],
 * which corresponds to the PDF f(x) = 2 - 2x
 * 
 * @author andrew
 *
 */
public class LabTwoValGeneratorTest {
  
  @Test
  public void testLabTwoValGenerator() {
    //number of tests per run
    int NUMTESTS = 10000; 
    // number of runs
    int NUMRUNS = 100;
    LabTwoValGenerator gen = new LabTwoValGenerator();
    for (int i = 0; i < NUMRUNS; i++) {
      // create a divider on [0, 1)
      double divider = Math.random(); 
      int rightSamples = 0; 
      // calculate the area of the pdf to the right of divider
      double rightArea = (1 - divider) * (1 - divider);
      for (int j = 0; j < NUMTESTS; j++) {
        // collect samples whose values are larger than divider
        if (gen.makeValuation() > divider)
          rightSamples++;
      }
      // calculate the ratio of samples larger than divider
      double rightRatio = rightSamples / (double) NUMTESTS;
      // assert that the ratio of samples is similar to the 
      // area of the region of the pdf to the right of divider
      // over repeated runs
      assertTrue(Math.abs(rightRatio - rightArea) < 0.02);
    }
  }
}