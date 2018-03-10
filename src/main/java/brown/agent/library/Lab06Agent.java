package brown.agent.library;

import brown.agent.AbsLab06Agent;
import brown.channels.library.CallMarketChannel;
import brown.exceptions.AgentCreationException;

public class Lab06Agent extends AbsLab06Agent{

  public Lab06Agent(String host, int port, String name)
      throws AgentCreationException {
    super(host, port, name);
  }

  @Override
  public void onMarketStart() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onTransaction(int quantity, double price) {
    // TODO Auto-generated method stub
    
  }
  
  public static void main(String[] args) throws AgentCreationException {
    // new Lab06Agent();
    while(true){}      
    
  }

}
