package brown.user.agent.library;

import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.system.setup.ISetup;
import brown.system.setup.library.Startup;
import brown.user.agent.IAgent;

public class SecondPriceAgent extends AbsAgent implements IAgent {

  public SecondPriceAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    System.out.print(informationMessage.toString());
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    System.out.println(valuationMessage.toString()); 
    
  }
  
  public static void main(String[] args) {
    new SecondPriceAgent("localhost", 2121, new Startup()); 
    while(true) {}
  }

}
