package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;

public class RegistrationResponseMessage extends AbsRegistrationResponseMessage implements IRegistrationResponseMessage {

  public RegistrationResponseMessage(Integer messageID, Integer agentID, String name) {
    super(messageID, agentID, name);
  }
  
}
