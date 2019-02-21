package brown.communication.messages;

import brown.communication.channel.IChannel;

public interface ITradeRequestMessage extends IMessage {
  
  public IChannel getChannel(); 
  
}
