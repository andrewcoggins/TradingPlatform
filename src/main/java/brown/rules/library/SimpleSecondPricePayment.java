package brown.rules.library;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.bid.library.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.tradeable.ITradeable;

/**
 * Payment rule for a simple second price auction. The agent to which
 * a good is allocated pays the second highest price (within their group)
 * for that good.
 * @author acoggins
 *
 */
public class SimpleSecondPricePayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    List<List<Integer>> grouping = state.getGroups();               
    List<Order> allOrders = new LinkedList<Order>();
    
    for (List<Integer> group : grouping){
      Map<ITradeable, Double> highest = new HashMap<ITradeable, Double>();
      Map<ITradeable, Integer> highestID = new HashMap<ITradeable, Integer>();
      Map<ITradeable, Double> secondHighest = new HashMap<ITradeable, Double>();
      
      List<ITradeable> allTradeables = state.getTradeables(); 
      for (ITradeable t : allTradeables) {
        highest.put(t, 0.0);
        secondHighest.put(t, 0.0);
      }
      Map<Integer, List<ITradeable>> allocation = state.getAllocation(); 
      List<TradeMessage> allBids = state.getBids(); 
      for (TradeMessage aMessage : allBids) {
        if (group.contains(aMessage.AgentID)){
          AuctionBidBundle aBundle = (AuctionBidBundle) aMessage.Bundle; 
          Map<ITradeable, BidType> aBid = aBundle.getBids().bids; 
          for (ITradeable t : aBid.keySet()) {
            if (allTradeables.contains(t)) {
              if (aBid.get(t).price > highest.get(t)) {
                highest.put(t, aBid.get(t).price);
                highestID.put(t, aMessage.AgentID);
              }
            }
          }          
        }
     }
      for (TradeMessage aMessage : allBids) {
        if (group.contains(aMessage.AgentID)) {        
          AuctionBidBundle aBundle = (AuctionBidBundle) aMessage.Bundle; 
          Map<ITradeable, BidType> aBid = aBundle.getBids().bids; 
          for (ITradeable t : aBid.keySet()) {
            if (allTradeables.contains(t)) {
              if (aBid.get(t).price > secondHighest.get(t) && highestID.get(t) != aMessage.AgentID) {
                secondHighest.put(t, aBid.get(t).price);
              }
            }
          }
        }
      }
      for (Entry<Integer, List<ITradeable>> anEntry : allocation.entrySet()) {
        if (group.contains(anEntry.getKey())){
          for (ITradeable aTradeable : anEntry.getValue()) {
            Order agentOrder = new Order(anEntry.getKey(), null, secondHighest.get(aTradeable), 1, aTradeable);
            allOrders.add(agentOrder);
          }          
        }
      }      
    }
    state.setPayments(allOrders);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub    
  }
}
