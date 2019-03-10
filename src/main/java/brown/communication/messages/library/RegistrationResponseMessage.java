package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.user.agent.IAgent;

public class RegistrationResponseMessage extends AbsRegistrationResponseMessage implements IRegistrationResponseMessage {

  public RegistrationResponseMessage(Integer messageID) {
    super(messageID);
    // TODO Auto-generated constructor stub
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }
  
  
}
