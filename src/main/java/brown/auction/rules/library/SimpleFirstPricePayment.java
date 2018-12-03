package brown.auction.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.OneSidedBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.library.Order;
import brown.platform.messages.library.TradeMessage;

/**
 * Simple simultaneous first price auction.
 * @author andrew
 *
 */
public class SimpleFirstPricePayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    List<List<Integer>> grouping = state.getGroups();               
    List<Order> allOrders = new LinkedList<Order>();
    
    for (List<Integer> group : grouping){
      Map<ITradeable, Double> highest = new HashMap<ITradeable, Double>();
      List<ITradeable> allTradeables = state.getTradeables(); 
      for (ITradeable t : allTradeables)
        highest.put(t,  0.0);

      Map<Integer, List<ITradeable>> allocation = state.getAllocation(); 
      List<TradeMessage> allBids = state.getBids(); 
      for (TradeMessage aMessage : allBids) {
        if (group.contains(aMessage.AgentID)){
          OneSidedBidBundle aBundle = (OneSidedBidBundle) aMessage.Bundle; 
          Map<ITradeable, BidType> aBid = aBundle.getBids().bids; 
          for (ITradeable t : aBid.keySet()) {
            if (aBid.get(t).price > highest.get(t)) {
              highest.put(t, aBid.get(t).price);
            }
          }
        }
      }
      for (Entry<Integer, List<ITradeable>> anEntry : allocation.entrySet()) {
        for (ITradeable aTradeable : anEntry.getValue()) {
          if (group.contains(anEntry.getKey())){          
            Order agentOrder = new Order(anEntry.getKey(), null, highest.get(aTradeable), 1, aTradeable);
            allOrders.add(agentOrder);
          }
        }
      }
    }
    state.setPayments(allOrders);
  }

  
}