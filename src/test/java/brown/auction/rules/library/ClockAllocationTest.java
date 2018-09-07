package brown.auction.rules.library;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.TradeMessage;

/*
 * test for the clock auction allocation rule. Rule should allocate tradeable
 * to the bidder who submitted the highest bid for that tradeable. 
 * @author June
*/
public class ClockAllocationTest {

  // test for basic functionality

  // TEST 1
  // three agents bid yes - step 1
  // two agents bid yes - step 2
  // one agent bid yes - step 3
  // -> allocate to the last bidder

  // TEST 2
  // three agents bid yes - step 1
  // two agents bid yes - step 2
  // no agent bid yes - step 3
  // -> randomly choose an agent 
  
  // TEST 3
  // no agent bid yes - step 1
  // -> randomly choose an agent 


  // on a single run, if there is a bid, no one is allocated
  // if there is no bid, then an agent is picked at random
  // from the alternative allocation (previous round). 
  @Test
  public void testClockAllocation() {
    ClockAllocation testRule = new ClockAllocation();
    
    // generate a market state
    ITradeable t = new SimpleTradeable(0);
    List<ITradeable> tradeableList = new LinkedList<ITradeable>();
    tradeableList.add(t);
    
    MarketState testState = new MarketState(0, tradeableList, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>();
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>();
    List<Integer> singleGroup = new LinkedList<Integer>();
    
    // previous allocation 3 agents bid. 
    for(int i = 0; i <= 2; i++) {
      Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>();
      bidMap.put(t, new BidType(0.0, 1));
      IBidBundle bidBundle = new AuctionBidBundle(bidMap);
      TradeMessage trade = new TradeMessage(i, bidBundle, i, i);
      testState.addBid(trade);
      allBids.add(trade);
      singleGroup.add(i);
    }
    
    allGroups.add(singleGroup);
    testState.setGroups(allGroups);
    
    // test that allocation rule allocates to correct agent.
    testRule.setAllocation(testState);
    Map<Integer, List<ITradeable>> expectedAllocation = new HashMap<Integer, List<ITradeable>>();
    expectedAllocation.put(0,  tradeableList);
    expectedAllocation.put(1,  tradeableList);
    expectedAllocation.put(2,  tradeableList);
    
    assertEquals(testState.getBids(), allBids);
    assertEquals(testState.getAllocation(), expectedAllocation);
    
  }
  
  @Test
  public void testClockAllocationTwo() {
    ClockAllocation testRule = new ClockAllocation();
    
    // generate a market state
    ITradeable t = new SimpleTradeable(0);
    List<ITradeable> tradeableList = new LinkedList<ITradeable>();
    tradeableList.add(t);
    
    MarketState testState = new MarketState(0, tradeableList, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>();
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>();
    List<Integer> singleGroup = new LinkedList<Integer>();
    
    // nobody bids    
    allGroups.add(singleGroup);
    testState.setGroups(allGroups);
    
    // test that allocation rule allocates to correct agent.
    testRule.setAllocation(testState);
    Map<Integer, List<ITradeable>> expectedAllocation = new HashMap<Integer, List<ITradeable>>();

    assertEquals(testState.getBids(), allBids);
    assertEquals(testState.getAllocation(), expectedAllocation);
    
  } 
  
  public static void main(String[] args) {
      ClockAllocationTest t = new ClockAllocationTest();
      t.testClockAllocation(); 
      t.testClockAllocationTwo(); 
    }
}
