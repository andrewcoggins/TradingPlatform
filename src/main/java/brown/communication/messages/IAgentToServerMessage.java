package brown.communication.messages;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messageserver.IMessageServer;

public interface IAgentToServerMessage extends IMessage {

  public void serverDispatch(Connection connection, IMessageServer server);
  
}
