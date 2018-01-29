package brown.rules.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.tradeable.ITradeable;

/**
 * Simple simultaneous first price auction.
 * @author andrew
 *
 */
public class SimpleFirstPricePayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    List<Order> allOrders = new LinkedList<Order>();
    Map<ITradeable, Double> highest = new HashMap<ITradeable, Double>();
    List<ITradeable> allTradeables = state.getTradeables(); 
    for (ITradeable t : allTradeables)
      highest.put(t,  0.0);
    // TODO Auto-generated method stub
    Map<Integer, List<ITradeable>> allocation = state.getAllocation(); 
    List<TradeMessage> allBids = state.getBids(); 
    for (TradeMessage aMessage : allBids) {
      AuctionBidBundle aBundle = (AuctionBidBundle) aMessage.Bundle; 
      Map<ITradeable, BidType> aBid = aBundle.getBids().bids; 
      for (ITradeable t : aBid.keySet()) {
        if (aBid.get(t).price > highest.get(t)) {
          highest.put(t, aBid.get(t).price);
        }
      }
    }
    for (Entry<Integer, List<ITradeable>> anEntry : allocation.entrySet()) {
      for (ITradeable aTradeable : anEntry.getValue()) {
        Order agentOrder = new Order(anEntry.getKey(), null, highest.get(aTradeable), 1, aTradeable);
        allOrders.add(agentOrder);
      }
    }
    state.setPayments(allOrders);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
  
}