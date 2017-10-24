package brown.agent;

import brown.channels.library.CDAAgentChannel;
import brown.channels.library.SimpleAuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.Setup;

public abstract class SimpleSealedAgent extends Agent {

  public SimpleSealedAgent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //Noop
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAuctionChannel market) {
    //Noop
  }
  
}