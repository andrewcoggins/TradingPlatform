package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.setup.ISetup;

public abstract class AbsSimpleSealedAgent extends AbsAgent {

  public AbsSimpleSealedAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //Noop
  }
  
  @Override
  public void onSimpleOpenOutcry(SimpleAgentChannel market) {
    //Noop
  }
  
  @Override
  public void onNegotiateRequest(NegotiateRequestMessage tradeRequest) {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void onTradeRequest(BidRequestMessage bidRequest) {
    // TODO Auto-generated method stub  
  }
  
}