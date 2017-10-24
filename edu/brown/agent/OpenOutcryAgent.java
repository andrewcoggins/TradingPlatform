package brown.agent;

import brown.channels.library.CDAAgentChannel;
import brown.channels.library.SimpleAuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.Setup;

public abstract class OpenOutcryAgent extends Agent {

  public OpenOutcryAgent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onSimpleSealed(SimpleAuctionChannel simpleWrapper) {
    //Noop
  }
  
  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //Noop
  }
  
}

