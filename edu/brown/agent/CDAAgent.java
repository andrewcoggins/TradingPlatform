package brown.agent;

import brown.channels.library.CDAAgentChannel;
import brown.channels.library.SimpleAuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.Setup;

public abstract class CDAAgent extends Agent {

  public CDAAgent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void onSimpleSealed(SimpleAuctionChannel simpleWrapper) {
    //Noop
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAuctionChannel market) {
    //Noop
  }
  
}