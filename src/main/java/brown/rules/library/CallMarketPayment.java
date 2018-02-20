package brown.rules.library;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.market.marketstate.IMarketState;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.Order;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;
import brown.rules.IPaymentRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;

public class CallMarketPayment  implements IPaymentRule {

  @Override
  public void setOrders(IMarketState state) {
    OrderBook book = state.getOrderBook();
    List<BuyOrder> buys = book.getBuys();
    List<SellOrder> sells = book.getSells();
        
    Collections.sort(buys,(h1, h2) -> -1*(h1.price.compareTo(h2.price)));
    Collections.sort(sells, (h1, h2) -> h1.price.compareTo(h2.price));
    
    Integer tradeableID  = ((SimpleTradeable) state.getTradeables().get(0)).ID;
    List<Order> orders = new LinkedList<Order>();
    Map<Integer,List<ITradeable>> alloc = new HashMap<Integer,List<ITradeable>>();

    boolean crossed = false;
    int buyIndex = 0;
    int sellIndex = 0;
    
    while (!crossed && buyIndex < buys.size() && sellIndex < sells.size()){
      BuyOrder highestBid = buys.get(buyIndex);
      SellOrder lowestOffer = sells.get(sellIndex);
      
      if (highestBid.price >= lowestOffer.price){
        // Update the allocation      
        
        Double price = (Double) ((highestBid.price + lowestOffer.price)/2);
        orders.add(new Order(highestBid.agent, lowestOffer.agent, price, 1, new SimpleTradeable(tradeableID)));
        buys.remove(buyIndex);
        sells.remove(sellIndex);
        
        // Update indices
        buyIndex++;
        sellIndex++;
      } else {
        // Else no more trades can be made
        crossed = false;
      }
    }
    
    state.setPayments(orders);
    
  }

  @Override
  public void reset() {
  }

}
