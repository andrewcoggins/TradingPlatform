package brown.auction.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.logging.library.Logging;
import brown.mechanism.bid.library.AuctionBid;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.BundleType;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.TradeMessage;

/**
 * Activity rule for a clock open outcry auction.
 * determines conditions for acceptable bidding and
 * increments the reserve prices.
 * @author acoggins
 *
 */
public class ClockActivity implements IActivityRule {
  private final Double increment; 
  private Map<Integer, Integer> agentToTick; 
  
  public ClockActivity(Double anIncrement) {
    this.increment = anIncrement; 
    // a map of the 'round' that each agent has most recently bid in. 
    this.agentToTick = new HashMap<Integer, Integer>(); 
  }

  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
      if (aBid.Bundle.getType() == BundleType.AUCTION) {
        AuctionBid bid = (AuctionBid) aBid.Bundle.getBids();
        double sumOfBids = 0;
        double sumOfReserves = 0;
        int currentTick = state.getTicks(); 
        boolean acceptable = true; 
        
        // make sure the agent hasn't already submitted a bid on the current tick.
        if (!this.agentToTick.containsKey(aBid.AgentID)) {
          this.agentToTick.put(aBid.AgentID, currentTick); 
        } else { 
          if (agentToTick.get(aBid.AgentID) == currentTick) {
            state.setAcceptable(false);
            return;
          }
        }
        // For every bid
        for (Entry<ITradeable,BidType> entry: bid.bids.entrySet()) { 
          List<SimpleTradeable> stList = entry.getKey().flatten();
          // Check if bid maps a single tradeable
          if (stList.size() > 1) {            
            acceptable = false; 
            state.setAcceptable(false);
            return;
          }
          // That tradeable must have valid ID
          for (SimpleTradeable st : stList) {
            if (st.ID < 0 || st.ID > 97) {
              acceptable = false;
              state.setAcceptable(false);
              return;            
              }
          }    
          sumOfBids += entry.getValue().price;
          sumOfReserves += state.getReserve().get(entry.getKey());
        }      
        if (sumOfBids < sumOfReserves) {
          acceptable = false;
          state.setAcceptable(false);
          return;          
        }
        state.setAcceptable(acceptable);
      } else {
        Logging.log("Wrong type of bid in auction phase");
        state.setAcceptable(false);
      }
  }

  @Override
  public void setReserves(IMarketState state) {
      Map<ITradeable, List<Integer>> alloc = state.getAltAlloc();
      Map<ITradeable, Double> currReserve = state.getReserve();
      Map<ITradeable, Double> newReserve = new HashMap<ITradeable, Double>();
      
      for (ITradeable t : state.getTradeables()) {
        Double currPrice = currReserve.get(t);
        Double newPrice = currPrice;
        if (alloc.containsKey(t)){
          if (alloc.get(t).size() > 1) {
            newPrice = newPrice + this.increment;
          }
        } else {
          Logging.log("MixedQueryClockActivity Error: Tradeables don't match up");
        }    
        newReserve.put(t,  newPrice);
      }        
      state.setReserve(newReserve);
    }

}
