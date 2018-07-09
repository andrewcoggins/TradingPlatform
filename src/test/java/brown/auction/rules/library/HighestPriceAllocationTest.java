package brown.auction.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.TradeMessage;

/**
 * test for the highest price allocation rule. Rule should allocate tradeable
 * to the bidder who submitted the highest bid for that tradeable.
 * @author andrew
 *
 */
public class HighestPriceAllocationTest {
  
  //test for basic functionality
  @Test
  public void testHighestPriceAllocation() {
    HighestPriceAllocation testRule = new HighestPriceAllocation(); 
    // generate a market state. 
    ITradeable t = new SimpleTradeable(0); 
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    tradeableList.add(t); 
    MarketState testState = new MarketState(0, tradeableList, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>(); 
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>(); 
    List<Integer> singleGroup = new LinkedList<Integer>(); 
    // generate some trade messages. These represent bids sent by agents.
    for(int i = 0; i <= 5; i++) {
      Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
      bidMap.put(t, new BidType((double) i , 1)); 
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
    Map<Integer, List<ITradeable>> expectedAllocation = 
        new HashMap<Integer, List<ITradeable>>();
    
    expectedAllocation.put(5, tradeableList); 
    // test that trade messages are in internal state.
    assertEquals(testState.getBids(), allBids);
    // make sure allocation is correct.
    assertEquals(testState.getAllocation(), expectedAllocation); 
  }
  
  @Test
  //no agents bid in the auction
  public void testHighestPriceAllocationTwo() {
    HighestPriceAllocation testRule = new HighestPriceAllocation(); 
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    MarketState testState = new MarketState(0, tradeableList, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>(); 
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>(); 
    List<Integer> singleGroup = new LinkedList<Integer>(); 
    
    allGroups.add(singleGroup); 
    testState.setGroups(allGroups);
    // test that allocation rule allocates to correct agent. 
    testRule.setAllocation(testState);
    Map<Integer, List<ITradeable>> expectedAllocation = 
        new HashMap<Integer, List<ITradeable>>();
     
    // test that trade messages are in internal state.
    assertEquals(testState.getBids(), allBids);
    // make sure allocation is correct.
    assertEquals(testState.getAllocation(), expectedAllocation); 
  }
  
  //multiple groups
  //agents submit negative value bids
  //agents submit incorrectly formatted bids.
  //other things to test: what about ties? 
  @Test
  public void testHighestPriceAllocationThree() {
    // make sure allocation is correct.
    //System.out.println(testState.getAllocation());
    HighestPriceAllocation testRule = new HighestPriceAllocation(); 
    // generate a market state. 
    ITradeable t = new SimpleTradeable(0); 
    List<ITradeable> tradeableList = new LinkedList<ITradeable>(); 
    tradeableList.add(t); 
    MarketState testState = new MarketState(0, tradeableList, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>(); 
    List<List<Integer>> allGroups = new LinkedList<List<Integer>>(); 
    List<Integer> singleGroup = new LinkedList<Integer>(); 
    // generate some trade messages. These represent bids sent by agents.
    for(int i = 0; i < 2; i++) {
      Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
      bidMap.put(t, new BidType(1.0 , 1)); 
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
    Map<Integer, List<ITradeable>> expectedAllocation = 
        new HashMap<Integer, List<ITradeable>>();
    
    expectedAllocation.put(0, tradeableList); 
    
    Map<Integer, List<ITradeable>> expectedAllocationTwo = 
        new HashMap<Integer, List<ITradeable>>();
    
    expectedAllocationTwo.put(1, tradeableList);
    
    // test that trade messages are in internal state.
    assertEquals(testState.getBids(), allBids);
    assertTrue(testState.getAllocation().equals(expectedAllocationTwo)
        || testState.getAllocation().equals(expectedAllocationTwo)); 
    
  }
  
  public static void main(String[] args) {
    HighestPriceAllocationTest t = new HighestPriceAllocationTest();
    t.testHighestPriceAllocation(); 
    t.testHighestPriceAllocationTwo(); 
    t.testHighestPriceAllocationThree();
  }
}