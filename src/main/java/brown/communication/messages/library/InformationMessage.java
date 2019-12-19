package brown.communication.messages.library;

import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IInformationMessage;
import brown.user.agent.IAgent;

public class InformationMessage extends AbsServerToAgentMessage implements IInformationMessage {

  private IMarketState publicState; 
  
  public InformationMessage() {
    super(null, null); 
  }
  
  public InformationMessage(int messageID, int agentID, IMarketState publicState) {
    super(messageID, agentID); 
    this.publicState = publicState; 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    agent.onInformationMessage(this);
  }
  
  public IMarketState getPublicState() {
    return this.publicState; 
  } 

}
