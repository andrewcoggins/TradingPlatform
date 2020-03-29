package brown.communication.messages;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messageserver.IOfflineMessageServer;
import brown.communication.messageserver.IOnlineMessageServer;
import brown.user.agent.IAgentBackend;

public interface IAgentToServerMessage extends IMessage {

  /**
   * Gets the ID of the message that this message is a response to.
   */
  public Integer getCorrespondingMessageID();
	
  /**
   * Figures out what type of message this is, and acts accordingly
   * @param connection
   * @param server
   */
  public void serverDispatch(Connection connection, IOnlineMessageServer server);
  
  public void offlineServerDispatch(IAgentBackend connection, IOfflineMessageServer server);
  
}
