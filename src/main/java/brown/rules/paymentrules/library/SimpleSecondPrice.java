package brown.rules.paymentrules.library;



import java.util.LinkedList;
import java.util.List;

import abrown.misc.library.Allocation;
import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.paymentrules.IPaymentRule;
import brown.setup.Logging;
import brown.tradeable.library.MultiTradeable;


public class SimpleSecondPrice implements IPaymentRule {

  @Override
  public void setPayments(ICompleteState state) {
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
          SimpleBid a = (SimpleBid) b.Bundle.getBids(); 
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

  @Override
  public void setPaymentType(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserve(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void permitShort(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

}
