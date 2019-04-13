package brown.communication.messages.library;

import brown.communication.messages.IValuationMessage;
import brown.user.agent.IAgent;

public class ValuationMessage extends AbsServerToAgentMessage implements IValuationMessage {

  public ValuationMessage() {
    super(null, null); 
  }
  
  public ValuationMessage(Integer messageID, Integer agentID) {
    super(messageID, agentID);
    // TODO Auto-generated constructor stub
  }
  

  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }

}
