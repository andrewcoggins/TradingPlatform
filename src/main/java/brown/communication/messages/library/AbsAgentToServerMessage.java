package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messageserver.IMessageServer;

public abstract class AbsAgentToServerMessage extends AbsMessage implements IAgentToServerMessage {

  public AbsAgentToServerMessage(Integer messageID, Integer agentID) {
    super(messageID, agentID);
  }
  
  @Override
  public abstract void serverDispatch(Connection connection, IMessageServer server); 

}
