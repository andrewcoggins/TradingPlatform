package brown.agent.library;

import java.util.PriorityQueue;

import brown.agent.AbsLab06Agent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;
import brown.market.marketstate.library.BuyOrder;
import brown.market.marketstate.library.OrderBook;
import brown.market.marketstate.library.SellOrder;

// Agent randomly either buys or sells and the middle price of spread
public class SpreadAgent extends AbsLab06Agent {

  public SpreadAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onTransaction(int quantity, double price) {

    // TODO Auto-generated method stub
    
  }

  @Override
  public void onMarketStart() {

    // TODO Auto-generated method stub
    
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    
    OrderBook ob = channel.getOrderBook();
    PriorityQueue<BuyOrder> buys = ob.getBuys();
    PriorityQueue<SellOrder> sells = ob.getSells();
    
    if(Math.random()<.5) {     
      this.buy(Math.floor(((sells.peek().price  - buys.peek().price)/2 )), 1, channel) ;
    }
    else{
      this.sell(Math.ceil(((sells.peek().price  - buys.peek().price)/2 )), 1, channel) ;

    }
    
  }

}
