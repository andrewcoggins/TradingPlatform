package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.PrevStateType;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.prevstate.library.PriceDiscoveryInfo;
import brown.auction.rules.IAllocationRule;
import brown.logging.library.Logging;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.bidbundle.library.BundleType;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.messages.library.TradeMessage;

/**
 * highest bidder allocation with reserve.
 * @author acoggins
 *
 */
public class HPReserveAllocation implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state) {
    // TODO Auto-generated method stub
    PrevStateInfo prev = state.getPrevState(); 
    if (prev.getType() == PrevStateType.DISCOVERY) {  
      PriceDiscoveryInfo pdInfo = (PriceDiscoveryInfo) prev;  
      Map<ITradeable, Double> reserve = pdInfo.reservePrices; 
      state.setReserve(reserve); 
    }
    //
    
    Map<ITradeable, Double> reserves = state.getReserve(); 
    AuctionBidBundle reservePrices = (AuctionBidBundle) reserves; 
    
    // a grouping is a list of lists of agent IDs.
    List<List<Integer>> grouping = state.getGroups();       
    Map<Integer,List<ITradeable>> alloc = new HashMap<Integer,List<ITradeable>>();

    for (List<Integer> group : grouping) {
      // What do you have to allocate
      List<SimpleTradeable> temp = new LinkedList<SimpleTradeable>();
      for (ITradeable t : state.getTradeables()){
        temp.addAll(t.flatten());
      }
      // Check that this is a set (SSSP doesn't make sense with duplicates)
      Set<SimpleTradeable> toAllocate = new HashSet<SimpleTradeable>();
      for (SimpleTradeable t : temp) {
        if (!toAllocate.contains(t)) {
          toAllocate.add(t);
        } else {
          Logging.log("[x] Highest Price Allocation: Initialized goods are not a set");
        }
      }
      
      Map<SimpleTradeable, Double> highestPrice = new HashMap<SimpleTradeable, Double>();
      Map<SimpleTradeable, Integer> highestAgent = new HashMap<SimpleTradeable, Integer>();
      
      List<TradeMessage> allBids = state.getBids();             
      for(TradeMessage bid : allBids) {      
        // Bid must be of right type
        if(bid.Bundle.getType().equals(BundleType.AUCTION) &&
           group.contains(bid.AgentID)) {
          AuctionBidBundle bundle = (AuctionBidBundle) bid.Bundle; 
          for (ITradeable t : bundle.getBids().bids.keySet()) {
            // This only works with SIMPLE Tradeables          
            if (t.getType() != TradeableType.Simple){
              Logging.log("[x] SSSP Allocation: Bid on non-Simple Tradeable");
            }
            // If either there are no bids for this yet or this bid is higher than those
            Map<ITradeable, BidType> reserveMap = reservePrices.getBids().bids; 
            if(highestPrice.get(t) == null || highestPrice.get(t) < bundle.getBids().bids.get(t).price) {
              if (reserveMap.containsKey(t)) {
                if (bundle.getBids().bids.get(t).price > reservePrices.getBids().bids.get(t).price) {
                  // Store the highest price and the corresponding agent
                  highestPrice.put((SimpleTradeable) t, bundle.getBids().bids.get(t).price);
                  highestAgent.put((SimpleTradeable) t, bid.AgentID);
                }
              } else { 
                // Store the highest price and the corresponding agent
                highestPrice.put((SimpleTradeable) t, bundle.getBids().bids.get(t).price);
                highestAgent.put((SimpleTradeable) t, bid.AgentID);
              }
            } else if (highestPrice.get(t) == bundle.getBids().bids.get(t).price && Math.random() > 0.5){
              if (reserveMap.containsKey(t)) {
                if (bundle.getBids().bids.get(t).price > reservePrices.getBids().bids.get(t).price) {
                  // Store the highest price and the corresponding agent
                  highestPrice.put((SimpleTradeable) t, bundle.getBids().bids.get(t).price);
                  highestAgent.put((SimpleTradeable) t, bid.AgentID);
                }
              } else { 
                highestPrice.put((SimpleTradeable) t, bundle.getBids().bids.get(t).price);
                highestAgent.put((SimpleTradeable) t, bid.AgentID); 
              }             
            }
          }
        }
      }
      
      for (Entry<SimpleTradeable,Integer> item : highestAgent.entrySet()) {
        Integer agentID = item.getValue();
        List<ITradeable> toAdd = new LinkedList<ITradeable>();
        // Check that we are not double allocating stuff
        if (toAllocate.contains(item.getKey())){
          toAllocate.remove(item.getKey());
        } else {
          Logging.log("[x] SSSPAllocation: Overallocated Good");
        }
        toAdd.add(item.getKey());
        if (!alloc.containsKey(agentID)){
          alloc.put(agentID, toAdd);
        } else {
          alloc.get(agentID).addAll(toAdd);
        }
      }    
    }
    state.setAllocation(alloc);
  }
  
}