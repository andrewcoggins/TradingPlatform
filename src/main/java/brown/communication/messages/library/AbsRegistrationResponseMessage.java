package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;

public abstract class AbsRegistrationResponseMessage extends AbsServerToAgentMessage implements IRegistrationResponseMessage {

  public AbsRegistrationResponseMessage(Integer messageID) {
    super(messageID);
  }

}
