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
import brown.accounting.bidbundle.library.Allocation;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.library.InternalState;
import brown.messages.library.BidMessage;
import brown.tradeable.library.Tradeable;

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
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    allGoods.add(new Tradeable(0));
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    aMap.put(new Tradeable(0), new MarketState(1, 1.0));
    SimpleBidBundle sim = new SimpleBidBundle(aMap);
    BidMessage b = new BidMessage(1, sim, 1, 1);
    state.addBid(b);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids;
    assertEquals(bids.get(new Tradeable(0)), aMap.get(new Tradeable(0)));
  }
  
  /*
   * one bidder, multiple goods.
   */
  @Test
  public void testTwo() {
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new Tradeable(i), new MarketState(1, 1.0));
    }
    SimpleBidBundle sim = new SimpleBidBundle(aMap);
    BidMessage b = new BidMessage(1, sim, 1, 1);
    state.addBid(b);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertEquals(bids.get(new Tradeable(i)), aMap.get(new Tradeable(i)));
    }
  }
  
  /*
   * multiple bidders, multiple goods. 
   */
  @Test
  public void testThree() {
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new Tradeable(i), new MarketState(1, 1.0));
    }
    Map<Tradeable, MarketState> aMapTwo = new HashMap<Tradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMapTwo.put(new Tradeable(i), new MarketState(2, 2.0));
    }
    SimpleBidBundle sim = new SimpleBidBundle(aMap);
    SimpleBidBundle simTwo = new SimpleBidBundle(aMapTwo);
    BidMessage b = new BidMessage(1, sim, 1, 1);
    BidMessage bTwo = new BidMessage(2, simTwo, 2, 2);
    state.addBid(b);
    state.addBid(bTwo);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertTrue(bids.get(new Tradeable(i)).PRICE == 2.0);
    }
  }
  
  /*
   * discards a complex bid bundle. 
   */
  @Test
  public void testFour() {
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    for (int i = 0; i < 5; i++) {
      allGoods.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
    for (int i = 0; i < 5; i++) {
      aMap.put(new Tradeable(i), new MarketState(1, 1.0));
    }
    Map<Set<Tradeable>, MarketState> aMapTwo = new HashMap<Set<Tradeable>, MarketState>();
    for (int i = 0; i < 5; i++) {
      Set<Tradeable> tSet = new HashSet<Tradeable>();
      tSet.add(new Tradeable(i));
      aMapTwo.put(tSet, new MarketState(2, 2.0));
    }
    SimpleBidBundle sim = new SimpleBidBundle(aMap);
    ComplexBidBundle simTwo = new ComplexBidBundle(aMapTwo, 0);
    BidMessage b = new BidMessage(1, sim, 1, 1);
    BidMessage bTwo = new BidMessage(2, simTwo, 2, 2);
    state.addBid(b);
    state.addBid(bTwo);
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids; 
    for(int i = 0; i < 5; i++) {
      assertTrue(bids.get(new Tradeable(i)).PRICE == 1.0);
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
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    allGoods.add(new Tradeable(0));
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    for (Integer bidder : bidderToValuation.keySet()) {
      Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
      aMap.put(new Tradeable(0), new MarketState(bidder, bidderToValuation.get(bidder)));
      SimpleBidBundle sim = new SimpleBidBundle(aMap);
      BidMessage aBid = new BidMessage(bidder, sim, 0, bidder);
      state.addBid(aBid);
    }
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids; 
    double highestBid = 0.0; 
    for(double p : bidderToValuation.values()) {
      if (p > highestBid) {
        highestBid = p;
      }
    }
    assertTrue(bids.get(new Tradeable(0)).PRICE == bidderToValuation.get(bids.get(new Tradeable(0)).AGENTID));
    assertTrue(bids.get(new Tradeable(0)).PRICE == highestBid);
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
    Set<Tradeable> allGoods = new HashSet<Tradeable>();
    for (int i = 0; i < GOODS; i++) {
      allGoods.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allGoods);
    SimpleHighestBidderAllocation s = new SimpleHighestBidderAllocation();
    for (Integer bidder : bidderToValuations.keySet()) {
      Map<Tradeable, MarketState> aMap = new HashMap<Tradeable, MarketState>();
      for(int i = 0; i < GOODS; i++) {
        aMap.put(new Tradeable(i), new MarketState(bidder, bidderToValuations.get(bidder).get(i)));
      }
      SimpleBidBundle sim = new SimpleBidBundle(aMap);
      BidMessage aBid = new BidMessage(bidder, sim, 0, bidder);
      state.addBid(aBid);
    }
    s.setAllocation(state);
    Allocation goods = (Allocation) state.getAllocation();
    Map<Tradeable, MarketState> bids = goods.getBids().bids; 
    for (int i = 0; i < GOODS; i++) {
      double highestBid = 0.0; 
      for(List<Double> p : bidderToValuations.values()) {
        if (p.get(i) > highestBid) {
          highestBid = p.get(i);
        }
      }
      assertTrue(bids.get(new Tradeable(i)).PRICE == bidderToValuations.get(bids.get(new Tradeable(i)).AGENTID).get(i));
      assertTrue(bids.get(new Tradeable(i)).PRICE == highestBid);
    }
  }
}



