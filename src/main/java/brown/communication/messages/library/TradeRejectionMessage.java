package brown.communication.messages.library;

import brown.communication.messages.IStatusMessage;

public class TradeRejectionMessage extends AbsStatusMessage implements IStatusMessage {

  public TradeRejectionMessage(Integer messageID, Integer agentID, String error) {
    super(messageID, agentID, error);
  }

}
