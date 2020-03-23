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

  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((publicState == null) ? 0 : publicState.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InformationMessage other = (InformationMessage) obj;
    if (publicState == null) {
      if (other.publicState != null)
        return false;
    } else if (!publicState.equals(other.publicState))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "InformationMessage [publicState=" + publicState + "]";
  } 

  
}
