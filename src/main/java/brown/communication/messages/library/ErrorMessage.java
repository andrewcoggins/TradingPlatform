package brown.communication.messages.library;

import brown.communication.messages.IErrorMessage;
import brown.user.agent.IAgent;

public class ErrorMessage extends AbsErrorMessage implements IErrorMessage {

  public ErrorMessage(Integer messageID, String error) {
    super(messageID, error);
  }

}
