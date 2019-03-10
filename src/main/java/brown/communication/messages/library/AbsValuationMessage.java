package brown.communication.messages.library;

import brown.communication.messages.IValuationMessage;
import brown.user.agent.IAgent;

public class AbsValuationMessage extends AbsServerToAgentMessage implements IValuationMessage {

  public AbsValuationMessage(Integer messageID) {
    super(messageID);
    // TODO Auto-generated constructor stub
  }
  

  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }

}
