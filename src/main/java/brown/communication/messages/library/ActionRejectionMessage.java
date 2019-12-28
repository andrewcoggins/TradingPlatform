package brown.communication.messages.library;

import brown.communication.messages.IStatusMessage;

public class ActionRejectionMessage extends AbsStatusMessage implements IStatusMessage {

  public ActionRejectionMessage(Integer messageID, Integer agentID, String error) {
    super(messageID, agentID, error);
  }

}
