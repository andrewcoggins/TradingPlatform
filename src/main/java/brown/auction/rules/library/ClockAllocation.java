package brown.auction.rules.library;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IAllocationRule;
import brown.logging.Logging;
import brown.mechanism.bid.AuctionBid;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.SimpleTradeable;
import brown.platform.messages.TradeMessage;

/**
 * Allocation rule for a clock auction.
 * keeps a history of old allocations and updates 
 * new allocations accordingly. 
 * I wonder if this could be done better.
 * @author acoggins
 *
 */
public class ClockAllocation implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state) {
    Map<ITradeable, List<Integer>> oldAlloc = state.getAltAlloc();

    Map<ITradeable, List<Integer>> newAlloc = new HashMap<ITradeable, List<Integer>>();
    
    List<TradeMessage> bids = state.getBids();
    for (TradeMessage bid: bids){
      
      // Break this bid down into individual tradeables
      Set<ITradeable> tradeables = ((AuctionBid)  bid.Bundle.getBids()).bids.keySet();
      List<SimpleTradeable> stradeables = new LinkedList<SimpleTradeable>();
      for (ITradeable t: tradeables){
        stradeables.addAll(t.flatten());
      }        
      // For each tradeable, add this agent to the list in newAlloc
      for (SimpleTradeable st : stradeables){
        List<Integer> currList = newAlloc.getOrDefault(st, new LinkedList<Integer>());
        currList.add(bid.AgentID);
        newAlloc.put(st, currList);
        if (!oldAlloc.containsKey(st)){
          Logging.log("MixedQueryClockAlloc Error: Tradeable type doesn't match up");
        }
      }
    }
    // This needs to revert reserves back
    Map<ITradeable, Double> reserves = state.getReserve();
    
    
    // Check things in oldAlloc that nobody wants any more in newAlloc
    for (ITradeable good : oldAlloc.keySet()){
      if (!newAlloc.containsKey(good)){
        List<Integer> agents = oldAlloc.get(good);
        if (agents.size() > 0){
          Collections.shuffle(agents);
          List<Integer> newList = new LinkedList<Integer>();
          newList.add(agents.get(0));
          newAlloc.put(good, newList);
          // dont reduce the price
          // reserves.put(good, reserves.get(good)-state.getFlatIncrement());            
         } else {
          newAlloc.put(good, new LinkedList<Integer>());
        }
      }
    }
    state.setReserve(reserves);
    state.setAltAlloc(newAlloc); 
    state.setAllocation(altAllocToAlloc(newAlloc));
  }
  
  private Map<Integer, List<ITradeable>> altAllocToAlloc(Map<ITradeable, List<Integer>> altAlloc) {
    Map<Integer, List<ITradeable>> toReturn = new HashMap<Integer, List<ITradeable>>();
    for (Entry<ITradeable,List<Integer>> entry : altAlloc.entrySet()){
      for (Integer agent: entry.getValue()){
        List<ITradeable> currList = toReturn.getOrDefault(agent, new LinkedList<ITradeable>());
        currList.add(entry.getKey());
        toReturn.put(agent,currList);
      }
    }
    return(toReturn);
  }
  
  @Override
  public void reset() {
    // noop
  }

}
