package brown.user.agent.library;

import java.util.PriorityQueue;

import brown.mechanism.channel.library.CallMarketChannel;
import brown.platform.twosided.BuyOrder;
import brown.platform.twosided.IOrderBook;
import brown.platform.twosided.SellOrder;

/**
 * Agent randomly either buys or sells and the middle price of spread
 * @author kerry
 *
 */
public class SpreadAgent extends AbsLab06Agent {

  public SpreadAgent(String host, int port, String name)
       {
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
