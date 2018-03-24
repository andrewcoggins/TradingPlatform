package brown.agent.library;

import brown.agent.AbsPredictionMarketAgent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;

// This agent buys or sells randomly with uniform distribution
public class RandomAgent extends AbsPredictionMarketAgent {

  public RandomAgent(String host, int port) throws AgentCreationException {
    super(host, port);
    // TODO Auto-generated constructor stub
  }
  
  public RandomAgent(String host, int port, String name) throws AgentCreationException {
    super(host, port, name);
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    
    
    if(Math.random()<.5) {    
      this.buy(Math.min( 99,Math.max(1, (int)(Math.random()*100))), 1, channel); 
    }
    else{
      this.sell(Math.max( 99,Math.max(1, (int)(Math.random()*100))), 1, channel); 
    }
    
  }

  @Override
  public void onTransaction(int quantity, double price) {

    // TODO Auto-generated method stub
    
  }

  @Override
  public void onMarketStart() {

    // TODO Auto-generated method stub
    
  }
  
  public static void main(String[] args) throws AgentCreationException {

    new RandomAgent("localhost", 2121,"Random1");    
    new RandomAgent("localhost", 2121,"Random2");    
    new RandomAgent("localhost", 2121,"Random3");    
    new RandomAgent("localhost", 2121,"Random4");    
    
      while(true){}      
  }

  @Override
  public double getHighestBuy() {

    // TODO Auto-generated method stub
    return 1;
  }

  @Override
  public double getLowestSell() {

    // TODO Auto-generated method stub
    return 99;
  }  
  
  
  

}
