package brown.communication.messages.library;

import brown.communication.messages.IStatusMessage;

public class ErrorMessage extends AbsStatusMessage implements IStatusMessage {
  
  public ErrorMessage() {
    super(); 
  }
  
  public ErrorMessage(Integer messageID, Integer agentID, String error) {
    super(messageID, agentID, error);
  }

}
