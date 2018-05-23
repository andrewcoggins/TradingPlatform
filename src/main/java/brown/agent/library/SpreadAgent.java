package brown.agent.library;

import java.util.PriorityQueue;

import brown.agent.AbsLab06Agent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.market.twosided.BuyOrder;
import brown.market.twosided.IOrderBook;
import brown.market.twosided.OrderBook;
import brown.market.twosided.SellOrder;

/**
 * Agent randomly either buys or sells and the middle price of spread
 * @author kerry
 *
 */
public class SpreadAgent extends AbsLab06Agent {

  public SpreadAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
  }

  @Override
  public void onTransaction(int quantity, double price) {
    
  }

  @Override
  public void onMarketStart() {

    
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    
    IOrderBook ob = channel.getOrderBook();
    PriorityQueue<BuyOrder> buys = ob.getBuys();
    PriorityQueue<SellOrder> sells = ob.getSells();
    
    if(buys.size()<1 || sells.size()<1) {
      return;
    }
    
    if(Math.random()<.5) {   
      this.buy(Math.floor(((sells.peek().price  - buys.peek().price)/2 )), 1, channel) ;
    }
    else{
      this.sell(Math.ceil(((sells.peek().price  - buys.peek().price)/2 )), 1, channel) ;

    }
    
  }

}
