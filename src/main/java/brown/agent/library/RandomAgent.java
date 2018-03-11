package brown.agent.library;

import brown.agent.AbsLab06Agent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;

// This agent buys or sells randomly with uniform distribution
public class RandomAgent extends AbsLab06Agent {

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
      this.buy(Math.max( 99,Math.max(1, (int)(Math.random()*100))), 1, channel); 
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
  
  
  

}
