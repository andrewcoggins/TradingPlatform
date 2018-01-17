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
import brown.tradeable.library.Tradeable;

/**
 * naive combinatorial auction allocation rule... highest bidder gets the good. no reserve.
 * @author andrew
 *
 */
public class ComplexHighestBidderAllocation implements IAllocationRule {

  @Override
  public void tick(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(ICompleteState state) {
    // map to fill and set in internal state.
    //brute force SPP
    Map<Set<Tradeable>, List<MarketState>> all = new HashMap<Set<Tradeable>, List<MarketState>>();
    Set<Set<Tradeable>> combinations = new HashSet<Set<Tradeable>>();
    List<TradeMessage> allBids = state.getBids();
    for (TradeMessage aBid : allBids) { 
      ComplexBidBundle bundle = (ComplexBidBundle) aBid.Bundle;
      Map<Set<Tradeable>, MarketState> bidMap = bundle.getBids().bids;
      for(Set<Tradeable> aSet : bidMap.keySet()) {
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
    Set<Tradeable> allTradeables = new HashSet<Tradeable>();
    for(Set<Tradeable> aMap : combinations) {
      for(Tradeable t: aMap) {
        allTradeables.add(t);
      }
    }
    Set<Set<Set<Tradeable>>> allSubsets = allSubsets(combinations, allTradeables, new HashSet<Set<Tradeable>>());
    //OK, now find the best one. 
    //mutually exclusive hashmap.
    Map<Set<Tradeable>, MarketState> toReturn = new HashMap<Set<Tradeable>, MarketState>();
    Map<Set<Tradeable>, MarketState> reserve = new HashMap<Set<Tradeable>, MarketState>();
    double highestBid = 0.0;
    double secondHighest = 0.0;
    for(Set<Set<Tradeable>> setSets : allSubsets) {
      Map<Set<Tradeable>, MarketState> current = new HashMap<Set<Tradeable>, MarketState>();
      double currentBid = 0.0; 
      for(Set<Tradeable> set : setSets) {
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
  private Set<Set<Set<Tradeable>>> allSubsets(Set<Set<Tradeable>> combinations, 
      Set<Tradeable> aSet, Set<Set<Tradeable>> current) {
    Set<Set<Set<Tradeable>>> setOfSetsOfSets = new HashSet<Set<Set<Tradeable>>>();
    if (aSet.isEmpty()) {
      setOfSetsOfSets.add(current);
      return setOfSetsOfSets;
    }
    for (Set<Tradeable> s : combinations) {
      if (subset(s, aSet)) { 
        current.add(s); 
        Set<Set<Tradeable>> combinationsCopy = new HashSet<Set<Tradeable>>(combinations);
        combinationsCopy.remove(s);
        Set<Tradeable> aSetCopy = new HashSet<Tradeable>(aSet);
        aSetCopy.removeAll(s);
        setOfSetsOfSets.addAll(allSubsets(combinationsCopy, aSetCopy, current));
        }
      }
    return setOfSetsOfSets;
  }
  
  private boolean subset(Set<Tradeable> setOne, Set<Tradeable> setTwo) { 
    for (Tradeable item : setOne) {
      if (setTwo.contains(item))
        continue; 
      else return false; 
    }
    return true; 
  }
  
  @Override
  public void setBidRequest(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isOver(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void withReserve(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getAllocationType(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }
  
  
}