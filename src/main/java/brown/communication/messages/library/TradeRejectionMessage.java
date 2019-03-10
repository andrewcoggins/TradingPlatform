package brown.communication.messages.library;

import brown.communication.messages.IStatusMessage;

public class TradeRejectionMessage extends AbsStatusMessage implements IStatusMessage {

  public TradeRejectionMessage(Integer messageID, String error) {
    super(messageID, error);
  }

}
