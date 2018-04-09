package brown.value.generator.library; 

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import ch.uzh.ifi.ce.mweiss.specval.model.UnsupportedBiddingLanguageException;
import ch.uzh.ifi.ce.mweiss.specvalopt.vcg.external.domain.AuctionResult;

/**
 * test for the specval generator. Since we didn't write the specval code, 
 * really just a sanity check more then anything.
 * @author acoggins
 *
 */
public class SpecValGeneratorTest {
  
  @Test
  public void testSpecValGenerator() {
    // make a specvalgenerator
    SpecValGenerator generator = new SpecValGenerator(1, 10); 
    try {
      // test the the specval generator generally does what it says it does...
      generator.makeValuations();
      Map<String, Map<Set<String>, Double>> allBids = generator
          .convertAllBidsToSimpleBids(generator.allBidderValuations);
      System.out.println(allBids); 
      assertTrue(allBids.get("0").size() == 10); 
      // test that the specvalgenerator solves a vcg auction
      Map<String, Map<Set<String>, Double>> types = new HashMap<String, Map<Set<String>, Double>>(); 
      Map<Set<String>, Double> bidderOne = new HashMap<Set<String>, Double>(); 
      Set<String> aBundle = new HashSet<String>(); 
      aBundle.add("1"); 
      aBundle.add("2"); 
      bidderOne.put(aBundle, 1.0); 
      Set<String> aBundleTwo = new HashSet<String>(); 
      aBundleTwo.add("2"); 
      aBundleTwo.add("3"); 
      bidderOne.put(aBundleTwo, 2.0); 
      Map<Set<String>, Double> bidderTwo = new HashMap<Set<String>, Double>(); 
      Set<String> bund = new HashSet<String>(); 
      bund.add("1"); 
      bidderTwo.put(bund, 0.6); 
      Set<String> bundTwo = new HashSet<String>(); 
      bundTwo.add("2"); 
      bidderTwo.put(bundTwo, 0.5); 
      Set<String> bundThree = new HashSet<String>(); 
      bundThree.add("3"); 
      bidderTwo.put(bundThree, 1.4); 
      types.put("0", bidderOne); 
      types.put("1", bidderTwo); 
      //System.out.println(types); 
      AuctionResult aResult = generator.runVCGWithGivenBids(allBids); 
      System.out.println(generator.getVcgAllocationSimpleBid(aResult)); 
    } catch (UnsupportedBiddingLanguageException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }
}