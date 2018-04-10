package brown.rules.library; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.IBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IInnerTC;
import brown.tradeable.ITradeable;

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
public class SomeBidsTermination implements IInnerTC {
  
  private Set<TradeMessage> messageCache; 
  
  public SomeBidsTermination() {
    this.messageCache = new HashSet<TradeMessage>(); 
  }
  
  @Override
  public void innerTerminated(IMarketState state) {
    if (!(state.getTicks() == 1)) {
      boolean liveBids = false; 
      // accepted bids
      List<TradeMessage> accepted = state.getBids(); 
      // bids to remove
      List<TradeMessage> toRemove = new LinkedList<TradeMessage>();
      for (TradeMessage t : accepted) {
        if (this.messageCache.contains(accepted)) {
          toRemove.add(t);  
        } else {
          this.messageCache.add(t); 
        }
      }
      accepted.removeAll(toRemove); 
      IBidBundle reserve = state.getReserve(); 
      Map<ITradeable, BidType> reserveMap = ((AuctionBid) reserve.getBids()).bids; 
      // check trades in accepted
      for (TradeMessage trade : accepted) {
        Map<ITradeable, BidType> agentMap = ((AuctionBid) trade.Bundle.getBids()).bids; 
        for (ITradeable t : (agentMap.keySet())) { 
          if (reserveMap.containsKey(t)) {
            if (reserveMap.get(t).price < agentMap.get(t).price) {
              liveBids = true; 
            }
          }
        }
      }
      if (!liveBids) {
        System.out.println("INNER TERMINATED");
        System.out.println("ENDING RESERVE:" + reserve);
        state.setInnerOver(true);
      }
    }
    
  }

  @Override
  public void reset() {
    this.messageCache.clear();
  }
  
}