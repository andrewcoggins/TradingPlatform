package brown.rules.allocationrules.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.allocationrules.IAllocationRule;
import brown.tradeable.library.MultiTradeable;

/**
 * naive combinatorial auction allocation rule... highest bidder gets the good. no reserve.
 * @author andrew
 *
 */
public class ComplexHighestBidderAllocation implements IAllocationRule {


  @Override
  public void setAllocation(ICompleteState state) {
    // map to fill and set in internal state.
    //brute force SPP
    Map<Set<MultiTradeable>, List<MarketState>> all = new HashMap<Set<MultiTradeable>, List<MarketState>>();
    Set<Set<MultiTradeable>> combinations = new HashSet<Set<MultiTradeable>>();
    List<TradeMessage> allBids = state.getBids();
    for (TradeMessage aBid : allBids) { 
      ComplexBidBundle bundle = (ComplexBidBundle) aBid.Bundle;
      Map<Set<MultiTradeable>, MarketState> bidMap = bundle.getBids().bids;
      for(Set<MultiTradeable> aSet : bidMap.keySet()) {
        if (all.keySet().contains(aSet)) {
          // update
          List<MarketState> stateList = all.get(aSet);
          stateList.add(bidMap.get(aSet));
          all.put(aSet, stateList);
        } else {
          // put it in. 
          List<MarketState> stateList = new LinkedList<MarketState>();
          stateList.add(bidMap.get(aSet));
          all.put(aSet, stateList);
        }
      }
      combinations.addAll(bidMap.keySet());
    }
    //get all tradeables being bid on. 
    Set<MultiTradeable> allTradeables = new HashSet<MultiTradeable>();
    for(Set<MultiTradeable> aMap : combinations) {
      for(MultiTradeable t: aMap) {
        allTradeables.add(t);
      }
    }
    Set<Set<Set<MultiTradeable>>> allSubsets = allSubsets(combinations, allTradeables, new HashSet<Set<MultiTradeable>>());
    //OK, now find the best one. 
    //mutually exclusive hashmap.
    Map<Set<MultiTradeable>, MarketState> toReturn = new HashMap<Set<MultiTradeable>, MarketState>();
    Map<Set<MultiTradeable>, MarketState> reserve = new HashMap<Set<MultiTradeable>, MarketState>();
    double highestBid = 0.0;
    double secondHighest = 0.0;
    for(Set<Set<MultiTradeable>> setSets : allSubsets) {
      Map<Set<MultiTradeable>, MarketState> current = new HashMap<Set<MultiTradeable>, MarketState>();
      double currentBid = 0.0; 
      for(Set<MultiTradeable> set : setSets) {
        List<MarketState> states = all.get(set);
        double highestState = 0.0; 
        int position = 0;
        for (MarketState aState : states) {
          if (aState.PRICE > highestState) {
            highestState = aState.PRICE;
            position = states.indexOf(aState);
          }
        }
          currentBid += highestState;
          current.put(set, states.get(position));
      }
      if(currentBid >= highestBid) {
        highestBid = currentBid;
        toReturn = current;
      } 
//      else if (currentBid <= highestBid && currentBid >= secondHighest) {
//        if (!current.equals(toReturn)) {
//          reserve = current; 
//        }
//      }
    }
    state.setAllocation(new ComplexBidBundle(toReturn, null));
    //the reserve bundle has to be the second bund
    //state.setReserveBundle(new ComplexBidBundle(reserve, null));
  }

  /**
   * takes care of the set packing problem very inefficiently.
   * @param all
   * @param aSet
   * @param current
   * @return
   */
  private Set<Set<Set<MultiTradeable>>> allSubsets(Set<Set<MultiTradeable>> combinations, 
      Set<MultiTradeable> aSet, Set<Set<MultiTradeable>> current) {
    Set<Set<Set<MultiTradeable>>> setOfSetsOfSets = new HashSet<Set<Set<MultiTradeable>>>();
    if (aSet.isEmpty()) {
      setOfSetsOfSets.add(current);
      return setOfSetsOfSets;
    }
    for (Set<MultiTradeable> s : combinations) {
      if (subset(s, aSet)) { 
        current.add(s); 
        Set<Set<MultiTradeable>> combinationsCopy = new HashSet<Set<MultiTradeable>>(combinations);
        combinationsCopy.remove(s);
        Set<MultiTradeable> aSetCopy = new HashSet<MultiTradeable>(aSet);
        aSetCopy.removeAll(s);
        setOfSetsOfSets.addAll(allSubsets(combinationsCopy, aSetCopy, current));
        }
      }
    return setOfSetsOfSets;
  }
  
  private boolean subset(Set<MultiTradeable> setOne, Set<MultiTradeable> setTwo) { 
    for (MultiTradeable item : setOne) {
      if (setTwo.contains(item))
        continue; 
      else return false; 
    }
    return true; 
  }
}