package brown.communication.messages.library;

import brown.communication.messages.ITradeRequestMessage;

public abstract class AbsTradeRequestMessage extends AbsServerToAgentMessage implements ITradeRequestMessage {
  
  public AbsTradeRequestMessage() {
    super(null); 
  }
  
  public AbsTradeRequestMessage(Integer messageID) {
    super(messageID);
  }
  
  
}
