package brown.communication.messages.library;

import brown.communication.messages.IStatusMessage;

public class ErrorMessage extends AbsStatusMessage implements IStatusMessage {

  public ErrorMessage(Integer messageID, String error) {
    super(messageID, error);
  }

}
