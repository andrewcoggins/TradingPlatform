package brown.rules.allocationrules.library;

// import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.bundles.BidBundle;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.marketinternalstates.SimpleInternalState;
import brown.messages.auctions.Bid;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

public class SimpleHighestKTest {	
	@Test
	public void testTwoBiddersTest() {
		int TRADEABLES = 1;

		System.out.println("---------- TWO BIDDER TEST ----------");		
	    Set<Asset> tradeables = new HashSet<Asset>();
	    Map<Tradeable, MarketState> bundle1 = new HashMap<Tradeable, MarketState>();
	    Map<Tradeable, MarketState> bundle2 = new HashMap<Tradeable, MarketState>();
	    
	    for(int i = 0; i < TRADEABLES; i++) {
	      // 1 is count 
	      tradeables.add(new Asset(new Tradeable(i), 1));
	      
	      // 2 agents
	      bundle1.put(new Tradeable(i), new MarketState(1, 0.0));
	      bundle2.put(new Tradeable(i), new MarketState(2, 1.0));
	    }
	    
	    // Create a market state
	    MarketInternalState testState = new SimpleInternalState(0, tradeables);
	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle1), 0, 1));	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle2), 0, 2));	    
	    
	   SimpleHighestBidderAllocation testRule = new SimpleHighestBidderAllocation();
	   BidBundle result = (SimpleBidBundle) testRule.getAllocation(testState);
	   
	   System.out.println("Results of Two Bidder Test ");
	   System.out.println(result.toString()); 		
	}
	
	@Test
	public void testMoreComplicated() {
	   System.out.println("---------- MULTI BIDDER TEST ----------");	
	   int TRADEABLES = 3; 
	   double[][] vals = {{1.,3.,5.},
	   					  {2.,4.,7.},
	   					  {6.,1.,2.},
	   					  {3.,8.,4.}};
	    Map<Tradeable, MarketState> bundle1 = new HashMap<Tradeable, MarketState>();
	    Map<Tradeable, MarketState> bundle2 = new HashMap<Tradeable, MarketState>();
	    Map<Tradeable, MarketState> bundle3 = new HashMap<Tradeable, MarketState>();
	    Map<Tradeable, MarketState> bundle4 = new HashMap<Tradeable, MarketState>();
	    Set<Asset> tradeables = new HashSet<Asset>();

	    for(int i = 0; i < TRADEABLES; i++) {
	      // 1 is count 
	      tradeables.add(new Asset(new Tradeable(i), 1));	      
	      // 4 agents
	      System.out.println(vals[0][i]);
	      System.out.println(vals[1][i]);
	      System.out.println(vals[2][i]);
	      System.out.println(vals[3][i]);
	      
	      bundle1.put(new Tradeable(i), new MarketState(0, vals[0][i]));
	      bundle2.put(new Tradeable(i), new MarketState(1, vals[1][i]));
	      bundle3.put(new Tradeable(i), new MarketState(2, vals[2][i]));
	      bundle4.put(new Tradeable(i), new MarketState(3, vals[3][i]));	      
	    }
	    
	    // Create a market state
	    MarketInternalState testState = new SimpleInternalState(0, tradeables);
	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle1), 0, 0));	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle2), 0, 1));	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle3), 0, 2));	    
	    testState.addBid(new Bid(0, new SimpleBidBundle(bundle4), 0, 3));	    
  
	   SimpleHighestBidderAllocation testRule = new SimpleHighestBidderAllocation();
	   BidBundle result = (SimpleBidBundle) testRule.getAllocation(testState);
	   
	   System.out.println("Results of Multi Bidder Test ");
	   System.out.println(result.toString()); 		
	}
}
