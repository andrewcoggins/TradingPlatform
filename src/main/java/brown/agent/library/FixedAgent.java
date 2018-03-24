package brown.agent.library;

import brown.agent.AbsPredictionMarketAgent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;


// Calculates fair value and then updates in direction of trades it does
public class FixedAgent extends AbsPredictionMarketAgent {

  private double fair_value = 0;
  private double spread_epsilon = 5;
  private int exposure=0; //Net number of contracts owned
  private int risklimit = 10;
  
  
  public FixedAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
    // TODO Auto-generated constructor stub
  }
  
  public FixedAgent(String host, int port, String name, double update, double spread)
      throws AgentCreationException {
    super(host, port, name);
    spread_epsilon = spread;
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onMarketStart() {

    // TODO Auto-generated method stub
    
    int decoys = this.getNumDecoys(); 
   
    if(this.getCoin()){
      fair_value = (double)(2+decoys)/(double)(2*decoys + 2) * 100;
    }
    else{   
      fair_value = (double)(decoys)/(double)(2*decoys + 2) * 100;
    }
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    
    if(exposure>=risklimit) {
      this.cancel(1, true, channel);
    }
    else {
      this.buy(Math.floor(fair_value-spread_epsilon), 1, channel);
    }
    
    if (exposure <= -1 * risklimit) {
      this.cancel(100, false, channel);
    }
    else {
      this.sell(Math.ceil(fair_value+spread_epsilon), 1, channel);
    }
    

  }

  @Override
  public void onTransaction(int quantity, double price) {
    
    if(price>0) { //contracts sold 
      exposure-= quantity;
    }
    else {
      exposure+= quantity;
    }
    
    
  }
  
  public static void main(String[] args) throws AgentCreationException {

    new FixedAgent("localhost", 2121,"Fixed1");    
//    new FixedAgent("localhost", 2121,"Fixed2");    
//    new FixedAgent("localhost", 2121,"Fixed3");    
//    new FixedAgent("localhost", 2121,"Fixed4");    
//    
      while(true){}      
  }

  @Override
  public double getHighestBuy() {

    // TODO Auto-generated method stub
    return fair_value-spread_epsilon;
  }

  @Override
  public double getLowestSell() {

    // TODO Auto-generated method stub
    return fair_value+spread_epsilon;
  }  
  

}
