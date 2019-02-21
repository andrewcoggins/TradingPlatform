package brown.communication.messages.library;

import brown.communication.channel.IChannel;
import brown.communication.messages.ITradeRequestMessage;
import brown.user.agent.library.AbsAgent;

public abstract class AbsTradeRequestMessage extends AbsMessage implements ITradeRequestMessage {
  
  
  private IChannel channel;
  
  public AbsTradeRequestMessage() {
    super(null); 
  }
  
  public AbsTradeRequestMessage(Integer messageID, IChannel channel) {
    super(messageID);
    this.channel = channel;
  }
  
  public IChannel getChannel() {
    return this.channel; 
  }
  
  @Override
  public void dispatch(AbsAgent agent) {    
    this.channel.dispatchMessage(agent);
  }
  
  @Override
  public String toString() {
    return "AbsTradeRequestMessage [channel=" + channel + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((channel == null) ? 0 : channel.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbsTradeRequestMessage other = (AbsTradeRequestMessage) obj;
    if (channel == null) {
      if (other.channel != null)
        return false;
    } else if (!channel.equals(other.channel))
      return false;
    return true;
  }
  
  
}
