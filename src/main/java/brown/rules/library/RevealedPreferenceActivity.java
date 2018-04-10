package brown.rules.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.IBidBundle;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;
import brown.tradeable.ITradeable;

/**
 * activity rule for revealed preferences.
 * useful in SMRA
 * @author acoggins
 *
 */

//TODO: issues with when the reserve price is incremented. 
public class RevealedPreferenceActivity implements IActivityRule {

  private final Map <ITradeable, Double> base; 
  private final Map <ITradeable, Double> increment; 
  // time series of demand sets.
  private Map<Integer, Map<Integer, Set<ITradeable>>> pastDemandSets; 
  
  public RevealedPreferenceActivity(Map<ITradeable, Double> aBase, Map<ITradeable, Double> anIncrement) {
    this.base = aBase;  
    this.increment = anIncrement; 
    this.pastDemandSets = new HashMap<Integer, Map<Integer, Set<ITradeable>>>(); 
  }
  
  
  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    boolean acceptable = true; 
    // check type is correct
    if (aBid.Bundle instanceof AuctionBidBundle) {
      Integer agentId = aBid.AgentID; 
      List<ITradeable> allTradeables = state.getTradeables();  
      AuctionBidBundle agentBundle = (AuctionBidBundle) aBid.Bundle; 
      Map<ITradeable, BidType> agentBids = agentBundle.getBids().bids; 
      System.out.println("AGENT BIDS: " + agentBids);
      AuctionBidBundle reserveBundle = (AuctionBidBundle) state.getReserve();  
      Map<ITradeable, BidType> reserves = reserveBundle.getBids().bids; 
      System.out.println("RESERVE PRICES: " + reserves); 
      // make sure the agent bid is not empty. 
      if (agentBids.isEmpty()) {
        System.out.println("UNACCEPTABLE: EMPTY BIDS"); 
        acceptable = false;
      }
      // get current demand set.
      // get ones that are above reserve prices. 
      Set<ITradeable> currentDemandSet = new HashSet<ITradeable>(); 
      for (ITradeable t : reserves.keySet()) {
        if (agentBids.containsKey(t)) {
          if (agentBids.get(t).price >= reserves.get(t).price) {
            currentDemandSet.add(t); 
          }
        }
      }
      // revealed preferences
      // we want to 
      for (int i = 1; i < state.getTicks(); i++) { 
        Map<ITradeable, Double> prevPrices = new HashMap<ITradeable, Double>(); 
        // get prices at some time s
        for (ITradeable t : allTradeables) {
          prevPrices.put(t, this.base.get(t) + (i * state.getIncrement().get(t))); 
        }
        // get demand set at some past time.
        Set<ITradeable> pastDemandSet = pastDemandSets.get(i).get(agentId);
        double total = 0.0;
        for (ITradeable t : currentDemandSet) {
          total += reserves.get(t).price; 
          total -= prevPrices.get(t); 
        }
        for (ITradeable t : pastDemandSet) {
          total += prevPrices.get(t); 
          total -= reserves.get(t).price; 
        }
        if (total > 0.00001) {  
          System.out.println(total);
          acceptable = false; 
          System.out.println("UNACCEPTABLE: REVEALED PREFERENCE RULE"); 
        }
      }
      if (acceptable) {
        // put in agents' active demand sets.
        if (pastDemandSets.containsKey(state.getTicks())) {
          Map<Integer, Set<ITradeable>> existing = pastDemandSets.get(state.getTicks()); 
          existing.put(agentId, currentDemandSet); 
          pastDemandSets.put(state.getTicks(), existing);
        } else{ 
          Map<Integer, Set<ITradeable>> agentDemand = new HashMap<Integer, Set<ITradeable>>();
          agentDemand.put(agentId, currentDemandSet);
          pastDemandSets.put(state.getTicks(), agentDemand); 
        } 
      } else {
        // put empty demand sets for agents.
        if (pastDemandSets.containsKey(state.getTicks())) {
          Map<Integer, Set<ITradeable>> existing = pastDemandSets.get(state.getTicks()); 
          existing.put(agentId, new HashSet<ITradeable>()); 
          pastDemandSets.put(state.getTicks(), existing);
        } else{ 
          Map<Integer, Set<ITradeable>> agentDemand = new HashMap<Integer, Set<ITradeable>>();
          agentDemand.put(agentId, new HashSet<ITradeable>());
          pastDemandSets.put(state.getTicks(), agentDemand); 
        } 
      }
    } else {
      acceptable = false; 
    }
    System.out.println("ACCEPTABLE: " + acceptable); 
    state.setAcceptable(acceptable); 
  }
  
  // increments reserve prices at time t if goods are in the demand set at time t - 1
  @Override
  public void setReserves(IMarketState state) {
    // set the increment in the market state.
    if (state.getIncrement().isEmpty()) {
      state.setIncrement(this.increment);
    }
    if (state.getReserve() != null) {
      // increments each of the prices for the tradeables if someone bid on them.
      IBidBundle aBundle = state.getReserve();  
      AuctionBid reserve = (AuctionBid) aBundle.getBids(); 
      Map<ITradeable, BidType> resMap = reserve.bids; 
      // if the tradeables are in the demand set at the last increment, 
      // increment their prices.
      Map<Integer, Set<ITradeable>> lastDemanded = pastDemandSets.get(state.getTicks() - 1); 
      System.out.println("LAST DEMANDED: " + lastDemanded);
      // check of the tradeables are in the last demand set.
      for(ITradeable t : resMap.keySet()) {
        boolean found = false; 
        for (Set<ITradeable> tSet : lastDemanded.values()) {
          if (tSet.contains(t)) { 
            found = true; 
            break; 
          }
        }
        // if so, increment their price.
        if (found) { 
          double inc = this.base.get(t) + (((double) state.getTicks() - 1) * state.getIncrement().get(t)); 
          resMap.put(t, new BidType(inc, 1)); 
          state.setReserve(new AuctionBidBundle(resMap)); 
        }
        // otherwise, the reserve price does not change. 
      }
    } else { 
      // initialize reserve
      Map<ITradeable, BidType> res = new HashMap<ITradeable, BidType>(); 
      for (ITradeable t : state.getTradeables()) {
        res.put(t, new BidType(0.0, 1)); 
      }
      IBidBundle init = new AuctionBidBundle(res); 
      state.setReserve(init);
    }
   }

  @Override
  public void reset() {
    this.pastDemandSets = new HashMap<Integer, Map<Integer, Set<ITradeable>>>(); 
  }
  
}