package brown.value.generator.library;

import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.ISAACRandom;
import org.junit.Test;

import brown.tradeable.library.MultiTradeable;

/**
 * tests the normal generator by constructing histograms of samples 
 * from my normal generator, an apache commons normal generator, and 
 * the random generator, and testing that the KS statistic of my 
 * normal generator and the apache generator is less than that of 
 * the random generator and the apache generator. Kind of trivial here
 * but this method may come in handy or other distributional tests.
 * @author andrew
 *
 */
public class NormalGeneratorTest {
  
  @Test
  public void testNormalGenerator() {
    int NUMTRIALS = 1000;
    int NUMBINS = 100;
    Double minBinVal = -3.0;
    Double maxBinVal = 5.0;
    Double binWidth = (maxBinVal - minBinVal) / NUMBINS;
    Integer[] normalHistogram = new Integer[NUMBINS];
    Integer[] ngHistogram = new Integer[NUMBINS];
    Integer[] randHistogram = new Integer[NUMBINS];
    for(int i = 0; i < NUMBINS; i++) {
      normalHistogram[i] = 0;
      ngHistogram[i] = 0; 
      randHistogram[i] = 0;
    }
    
       
    
   
    Function<Integer, Double> valFunc = x -> (double) x; 
    NormalGenerator ng = new NormalGenerator(valFunc, 1.0);
    NormalDistribution normalDist = new NormalDistribution(new ISAACRandom(), 1.0, 1.0);
    ValRandGenerator nullGenerator = new ValRandGenerator(minBinVal, maxBinVal);
    MultiTradeable good = new MultiTradeable(0);
    for(int i = 0; i < NUMTRIALS; i++) {
     Double ngValue = ng.makeValuation(good).value;
     Double nullValue = nullGenerator.makeValuation(good).value;
     Double normalValue = normalDist.sample();
     int ngBin = (int) ((ngValue - minBinVal) / binWidth);
     if(ngBin < NUMBINS)
       ngHistogram[ngBin] += 1;
     int nullBin = (int) ((nullValue - minBinVal) / binWidth);
     randHistogram[nullBin] += 1;
     int normalBin = (int) ((normalValue - minBinVal) / binWidth);
     if(normalBin < NUMBINS)
       normalHistogram[normalBin] += 1;
    }
    int nullKS = 0; 
    int ngKS = 0;
    for(int i = 0; i < NUMBINS; i++) {
      int nullComparison = Math.abs(normalHistogram[i] - randHistogram[i]);
      int ngComparison = Math.abs(normalHistogram[i] - ngHistogram[i]);
      nullKS =  nullComparison > nullKS ? nullComparison : nullKS;
      ngKS = ngComparison > ngKS ? ngComparison : ngKS;
    }
    
    //assert that normal generator produces values significantly closer to a normal
    //distribution than does a random generator.
    System.out.println(nullKS);
    System.out.println(ngKS);
    assertTrue(nullKS > ngKS);
  }
}