package brown.communication.messages;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messageserver.IMessageServer;

public interface IAgentToServerMessage extends IMessage {

  /**
   * Figures out what type of message this is, and acts accordingly
   * @param connection
   * @param server
   */
  public void serverDispatch(Connection connection, IMessageServer server);
  
}
