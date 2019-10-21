package brown.communication.messages.library;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.IInformationMessage;
import brown.user.agent.IAgent;

public class InformationMessage extends AbsServerToAgentMessage implements IInformationMessage {

  private IMarketPublicState publicState; 
  
  public InformationMessage() {
    super(null, null); 
  }
  
  public InformationMessage(int messageID, int agentID, IMarketPublicState publicState) {
    super(messageID, agentID); 
    this.publicState = publicState; 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    agent.onInformationMessage(this);
  }
  
  public IMarketPublicState getPublicState() {
    return this.publicState; 
  } 

}
