package brown.communication.messages.library;

import brown.communication.messages.IErrorMessage;
import brown.user.agent.IAgent;

public abstract class AbsErrorMessage extends AbsServerToAgentMessage implements IErrorMessage {
  
  private String error; 
  
  public AbsErrorMessage(Integer messageID, String error) {
    super(messageID);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    
  }
  
  @Override
  public String getError() {
    return this.error; 
  }
  
}
