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

public class AscendingActivity implements IActivityRule {

  private final Map <ITradeable, Double> base; 
  private final Map <ITradeable, Double> increment; 
  private Map<Integer, Set<ITradeable>> pastDemand; 
  
  public AscendingActivity(Map<ITradeable, Double> aBase, Map<ITradeable, Double> anIncrement) {
    this.base = aBase;  
    this.increment = anIncrement; 
    this.pastDemand = new HashMap<Integer, Set<ITradeable>>();
  } 
  
  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    boolean acceptable = true; 
    if (aBid.Bundle instanceof AuctionBidBundle) {
      Integer agentId = aBid.AgentID;  
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
      this.pastDemand.put(agentId, currentDemandSet); 
    } else {
      acceptable = false; 
    }
    state.setAcceptable(acceptable);
  }

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
      System.out.println("LAST DEMANDED: " + pastDemand);
      // check of the tradeables are in the last demand set.
      for(ITradeable t : resMap.keySet()) {
        boolean found = false; 
        for (Set<ITradeable> tSet : pastDemand.values()) {
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
    this.pastDemand.clear();
  }

  @Override
  public void reset() {
    this.pastDemand.clear();
  } 
  
}