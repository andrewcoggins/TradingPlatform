package brown.rules.library;



import java.util.LinkedList;
import java.util.List;

import brown.accounting.MarketState;
import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.IBidBundle;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.bid.library.AuctionBid;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Allocation;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.setup.Logging;
import brown.tradeable.library.MultiTradeable;


public class SimpleSecondPrice implements IPaymentRule {

  @Override
  public void setPayments(IMarketState state) {
    // TODO Auto-generated method stub
    IBidBundle highest = state.getAllocation();
    Allocation bundle = (Allocation) highest;      
    //get the bids again.
    //loop through the tradeables, get the winner of each tradeable 
    List<Order> payments = new LinkedList<Order>();
    for (MultiTradeable t : bundle.getBids().bids.keySet()) {
      //find the highest bidder
      int highestBidder = bundle.getBids().bids.get(t).AGENTID;
      //go through all the bids and 
      double nextHighest = -1000;
      for (TradeMessage b : state.getBids()) {
        if (b.AgentID != highestBidder) { 
          AuctionBid a = (AuctionBid) b.Bundle.getBids(); 
              if (a.bids.get(t).PRICE > nextHighest){
                nextHighest = a.bids.get(t).PRICE;
              }
            }
          }     
      payments.add(new Order(highestBidder, null, nextHighest, 1, t));
    }
    System.out.println(payments);
    state.setPayments(payments);
  }



}
