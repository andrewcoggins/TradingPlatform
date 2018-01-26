package brown.rules.library;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.bidbundle.BundleType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.IPaymentRule;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;


public class SSSPPayment implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    
    Map<SimpleTradeable, Double> highestPrice = new HashMap<SimpleTradeable, Double>();
    Map<SimpleTradeable, Double> secondHighestPrice = new HashMap<SimpleTradeable, Double>();    
    Map<SimpleTradeable, Integer> highestAgent = new HashMap<SimpleTradeable, Integer>();
    
    List<TradeMessage> allBids = state.getBids();             
    for(TradeMessage bid : allBids) {      
      // Bid must be of right type
      if(bid.Bundle.getType().equals(BundleType.AUCTION)) {
        AuctionBidBundle bundle = (AuctionBidBundle) bid.Bundle; 
        for (ITradeable t : bundle.getBids().bids.keySet()) {
          double currPrice = bundle.getBids().bids.get(t);
          // SimpleTradeable is checked for in the allocation rule                    
          if (highestPrice.get(t) == null){
            // No bids for this yet            
            highestPrice.put((SimpleTradeable) t, currPrice);
            highestAgent.put((SimpleTradeable) t, bid.AgentID);
          } else if (highestPrice.get(t) < currPrice){          
            // Bids already exist - push highest to second highest
            secondHighestPrice.put((SimpleTradeable) t, highestPrice.get(t));
            highestPrice.put((SimpleTradeable) t, currPrice);
            highestAgent.put((SimpleTradeable) t, bid.AgentID);            
          }
          }
        }
      }
    
    List<Order> orders = new LinkedList<Order>();
    for (SimpleTradeable t : highestAgent.keySet()){
      Logging.log("2nd highest :" + secondHighestPrice);
      orders.add(new Order(highestAgent.get(t), null, secondHighestPrice.get(t),1,t));
    }
    state.setPayments(orders);
  }

  @Override
  public void permitShort(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
}
