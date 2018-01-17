package brown.rules.allocationrules.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.accounting.MarketState;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.allocationrules.IAllocationRule;
import brown.tradeable.library.Good;

/**
 * naive combinatorial auction allocation rule... highest bidder gets the good. no reserve.
 * @author andrew
 *
 */
public class ComplexHighestBidderAllocation implements IAllocationRule {

  @Override
  public void tick(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(IMarketState state) {
    // map to fill and set in internal state.
    //brute force SPP
    Map<Set<Good>, List<MarketState>> all = new HashMap<Set<Good>, List<MarketState>>();
    Set<Set<Good>> combinations = new HashSet<Set<Good>>();
    List<TradeMessage> allBids = state.getBids();
    for (TradeMessage aBid : allBids) { 
      ComplexBidBundle bundle = (ComplexBidBundle) aBid.Bundle;
      Map<Set<Good>, MarketState> bidMap = bundle.getBids().bids;
      for(Set<Good> aSet : bidMap.keySet()) {
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
    Set<Good> allTradeables = new HashSet<Good>();
    for(Set<Good> aMap : combinations) {
      for(Good t: aMap) {
        allTradeables.add(t);
      }
    }
    Set<Set<Set<Good>>> allSubsets = allSubsets(combinations, allTradeables, new HashSet<Set<Good>>());
    //OK, now find the best one. 
    //mutually exclusive hashmap.
    Map<Set<Good>, MarketState> toReturn = new HashMap<Set<Good>, MarketState>();
    Map<Set<Good>, MarketState> reserve = new HashMap<Set<Good>, MarketState>();
    double highestBid = 0.0;
    double secondHighest = 0.0;
    for(Set<Set<Good>> setSets : allSubsets) {
      Map<Set<Good>, MarketState> current = new HashMap<Set<Good>, MarketState>();
      double currentBid = 0.0; 
      for(Set<Good> set : setSets) {
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
  private Set<Set<Set<Good>>> allSubsets(Set<Set<Good>> combinations, 
      Set<Good> aSet, Set<Set<Good>> current) {
    Set<Set<Set<Good>>> setOfSetsOfSets = new HashSet<Set<Set<Good>>>();
    if (aSet.isEmpty()) {
      setOfSetsOfSets.add(current);
      return setOfSetsOfSets;
    }
    for (Set<Good> s : combinations) {
      if (subset(s, aSet)) { 
        current.add(s); 
        Set<Set<Good>> combinationsCopy = new HashSet<Set<Good>>(combinations);
        combinationsCopy.remove(s);
        Set<Good> aSetCopy = new HashSet<Good>(aSet);
        aSetCopy.removeAll(s);
        setOfSetsOfSets.addAll(allSubsets(combinationsCopy, aSetCopy, current));
        }
      }
    return setOfSetsOfSets;
  }
  
  private boolean subset(Set<Good> setOne, Set<Good> setTwo) { 
    for (Good item : setOne) {
      if (setTwo.contains(item))
        continue; 
      else return false; 
    }
    return true; 
  }
  
  @Override
  public void setBidRequest(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isOver(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void withReserve(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getAllocationType(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(IMarketState state) {
    // TODO Auto-generated method stub
    
  }
  
  
}