package brown.rules.allocationrules.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.library.Allocation;
import brown.market.marketstate.library.CompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.library.SimpleHighestBidderAllocation;
import brown.tradeable.library.MultiTradeable;

/**
 * test for simple highest bidder allocation rule. 
 * C
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocationTest {
   
  /*
   * a simple test.
   */
  @Test
  public void testSimpleHighestBidderAllocation() {
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    allGoods.add(new MultiTradeable(0));
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    aMap.put(new MultiTradeable(0), new MarketState(1, 1.0));
    AuctionBidBundle sim = new AuctionBidBundle(aMap);
    TradeMessage b = new TradeMessage(1, sim, 1, 1);
    state.addBid(b);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids;
    assertEquals(bids.get(new MultiTradeable(0)), aMap.get(new MultiTradeable(0)));
  }
  
  /*
   * one bidder, multiple goods.
   */
  @Test
  public void testTwo() {
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new MultiTradeable(i), new MarketState(1, 1.0));
    }
    AuctionBidBundle sim = new AuctionBidBundle(aMap);
    TradeMessage b = new TradeMessage(1, sim, 1, 1);
    state.addBid(b);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertEquals(bids.get(new MultiTradeable(i)), aMap.get(new MultiTradeable(i)));
    }
  }
  
  /*
   * multiple bidders, multiple goods. 
   */
  @Test
  public void testThree() {
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new MultiTradeable(i), new MarketState(1, 1.0));
    }
    Map<MultiTradeable, MarketState> aMapTwo = new HashMap<MultiTradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMapTwo.put(new MultiTradeable(i), new MarketState(2, 2.0));
    }
    AuctionBidBundle sim = new AuctionBidBundle(aMap);
    AuctionBidBundle simTwo = new AuctionBidBundle(aMapTwo);
    TradeMessage b = new TradeMessage(1, sim, 1, 1);
    TradeMessage bTwo = new TradeMessage(2, simTwo, 2, 2);
    state.addBid(b);
    state.addBid(bTwo);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertTrue(bids.get(new MultiTradeable(i)).PRICE == 2.0);
    }
  }
  
  /*
   * discards a complex bid bundle. 
   */
  @Test
  public void testFour() {
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new MultiTradeable(i), new MarketState(1, 1.0));
    }
    Map<Set<MultiTradeable>, MarketState> aMapTwo = new HashMap<Set<MultiTradeable>, MarketState>();
    for (int i = 0; i < 5; i++) {
      Set<MultiTradeable> tSet = new HashSet<MultiTradeable>();
      tSet.add(new MultiTradeable(i));
      aMapTwo.put(tSet, new MarketState(2, 2.0));
    }
    AuctionBidBundle sim = new AuctionBidBundle(aMap);
    ComplexBidBundle simTwo = new ComplexBidBundle(aMapTwo, 0);
    TradeMessage b = new TradeMessage(1, sim, 1, 1);
    TradeMessage bTwo = new TradeMessage(2, simTwo, 2, 2);
    state.addBid(b);
    state.addBid(bTwo);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertTrue(bids.get(new MultiTradeable(i)).PRICE == 1.0);
    }
  }
  
  /*
   * something larger... a realistic setting. 
   */
  @Test
  public void realisticTest() {
    Map<Integer, Double> bidderToValuation = new HashMap<Integer, Double>();
    for (int i = 0; i < 10; i++) { 
      Double aNum = Math.random();
      bidderToValuation.put(i, aNum);
    }
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    allGoods.add(new MultiTradeable(0));
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    for (Integer bidder : bidderToValuation.keySet()) {
      Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
      aMap.put(new MultiTradeable(0), new MarketState(bidder, bidderToValuation.get(bidder)));
      AuctionBidBundle sim = new AuctionBidBundle(aMap);
      TradeMessage aBid = new TradeMessage(bidder, sim, 0, bidder);
      state.addBid(aBid);
    }
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids; 
    double highestBid = 0.0; 
    for(double p : bidderToValuation.values()) {
      if (p > highestBid) {
        highestBid = p;
      }
    }
    assertTrue(bids.get(new MultiTradeable(0)).PRICE == bidderToValuation.get(bids.get(new MultiTradeable(0)).AGENTID));
    assertTrue(bids.get(new MultiTradeable(0)).PRICE == highestBid);
  }
  
  /*
   * a realistic setting over multiple goods.
   */
  @Test
  public void realisticTestTwo() {
    int AGENTS = 10; 
    int GOODS = 8;
    
    Map<Integer, List<Double>> bidderToValuations = new HashMap<Integer, List<Double>>();
    for (int i = 0; i < AGENTS; i++) {
      List<Double> valuations = new LinkedList<Double>();
      for (int j = 0; j < GOODS; j++) {
        Double aNum = Math.random();
        valuations.add(aNum);
      }
      bidderToValuations.put(i, valuations);
    }
    Set<MultiTradeable> allGoods = new HashSet<MultiTradeable>();
    for (int i = 0; i < GOODS; i++) {
      allGoods.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    for (Integer bidder : bidderToValuations.keySet()) {
      Map<MultiTradeable, MarketState> aMap = new HashMap<MultiTradeable, MarketState>();
      for(int i = 0; i < GOODS; i++) {
        aMap.put(new MultiTradeable(i), new MarketState(bidder, bidderToValuations.get(bidder).get(i)));
      }
      AuctionBidBundle sim = new AuctionBidBundle(aMap);
      TradeMessage aBid = new TradeMessage(bidder, sim, 0, bidder);
      state.addBid(aBid);
    }
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<MultiTradeable, MarketState> bids = goods.getBids().bids; 
    for (int i = 0; i < GOODS; i++) {
      double highestBid = 0.0; 
      for(List<Double> p : bidderToValuations.values()) {
        if (p.get(i) > highestBid) {
          highestBid = p.get(i);
        }
      }
      assertTrue(bids.get(new MultiTradeable(i)).PRICE == bidderToValuations.get(bids.get(new MultiTradeable(i)).AGENTID).get(i));
      assertTrue(bids.get(new MultiTradeable(i)).PRICE == highestBid);
    }
  }
}



