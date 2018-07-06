package brown.auction.rules.library; 

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.ITerminationCondition;
import brown.mechanism.bid.library.AuctionBid;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.library.TradeMessage;

/**
 * Inner Termination condition- 
 * If any (valid) bids are received by the market, 
 * the market is not terminated. 
 * If there are no bids, the market terminates.
 * useful for ascending (English) auctions.
 * @author acoggins
 *
 */

//TODO: work with revealed pref edge case.
// solution: only check bids from each round. 
public class SomeBidsTermination implements ITerminationCondition {
  
  private List<TradeMessage> roundTrades; 
  private int prevSize; 
  
  public SomeBidsTermination() {
    this.roundTrades = new LinkedList<TradeMessage>(); 
    this.prevSize = 0; 
  }
  
  @Override
  public void isTerminated(IMarketState state) {
    if (!(state.getTicks() == 1)) {
      boolean liveBids = false; 
      // accepted bids
      List<TradeMessage> accepted = state.getBids(); 
      int currentSize = accepted.size(); 
      for (int i = 0; i < currentSize - this.prevSize; i++) { 
        roundTrades.add(accepted.get(prevSize + i)); 
      }
      Map<ITradeable, Double> reserve = state.getReserve(); 
      // check trades in accepted
      for (TradeMessage trade : roundTrades) {
        Map<ITradeable, BidType> agentMap = ((AuctionBid) trade.Bundle.getBids()).bids; 
        for (ITradeable t : (agentMap.keySet())) { 
          if (reserve.containsKey(t)) {
            if (reserve.get(t) < agentMap.get(t).price) {
              liveBids = true; 
            }
          }
        }
      }
      if (!liveBids) {
        System.out.println("INNER TERMINATED");
        System.out.println("ENDING RESERVE:" + reserve);
        state.setOver(true);
      }
    }
    
  }

  @Override
  public void reset() {
    this.roundTrades.clear(); 
    this.prevSize = 0; 
  }
  
}