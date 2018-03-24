package brown.agent.library;

import brown.agent.AbsPredictionMarketAgent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;


// Calculates fair value and then updates in direction of trades it does
public class UpdateAgent extends AbsPredictionMarketAgent {

  private double fair_value = 0;
  private double update_epsilon = 1;
  private double spread_epsilon = 5;
  boolean updated = false;
  private int exposure=0; //Net number of contracts owned
  private int risklimit = 10;
  
  public UpdateAgent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
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
    
    switch(decoys) {
    case 1:
      update_epsilon=0.5;
      spread_epsilon=3;
      break;
    case 2:
      update_epsilon=1;
      spread_epsilon=5;

      break;
    case 3:
      update_epsilon=2;
      spread_epsilon=7;
      break;
    default:
      update_epsilon=4;
      spread_epsilon=10;
    }
    
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    
    if(updated) {
    this.cancel(1, true, channel);
    this.cancel(100, false, channel);
    updated = false;
    }
    
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

    // TODO Auto-generated method stub
    if(price>0) {
      fair_value+=update_epsilon;
      fair_value = Math.min(fair_value, 94);
    }
    else{
      fair_value-=update_epsilon;
      fair_value = Math.max(fair_value, 06);

    }
    updated = true;
    
    
    
    if(price>0) { //contracts sold 
      exposure-= quantity;
    }
    else {
      exposure+= quantity;
    }
  }
  
  public static void main(String[] args) throws AgentCreationException {

    new UpdateAgent("localhost", 2121,"Update1");    
    new UpdateAgent("localhost", 2121,"Update2");    
    new UpdateAgent("localhost", 2121,"Update3");    
    new UpdateAgent("localhost", 2121,"Update4");    
    
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
