import brown.agent.AbsLab06Agent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;


// Calculates fair value and then updates in direction of trades it does
public class UpdateAgent extends AbsLab06Agent {

  private double fair_value = 0;
  private double epsilon = 5;
  boolean updated = false;
  
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
      fair_value = (double)(2+decoys)/(double)(2*decoys + 2);
    }
    else{   
      fair_value = (double)(decoys)/(double)(2*decoys + 2);
    }
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    
    if(updated) {
    this.cancel(0, true, channel);
    this.cancel(100, false, channel);
    updated = false;
    }
    
    this.buy(Math.floor(fair_value-epsilon), 1, channel);
    this.buy(Math.ceil(fair_value+epsilon), 1, channel);
  }

  @Override
  public void onTransaction(int quantity, double price) {

    // TODO Auto-generated method stub
    if(quantity>0) {
      fair_value+=epsilon;
    }
    else{
      fair_value-=epsilon;
    }
    updated = true;
  }

}
