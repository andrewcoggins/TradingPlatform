package brown.communication.messageserver;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;

/**
 * Interface for communication between server and agents. 
 * @author andrewcoggins
 *
 */
public interface IMessageServer {

  // server receives registration message from agent. 
  public void onRegistration(Connection connection, IRegistrationMessage registrationMessage); 
  
  // server receives bid message from agent. 
  public void onBid(Connection connection, ITradeMessage bidMessage); 
  
  // server sends message to agent.
  public void sendMessage(Connection connection, IServerToAgentMessage message); 
  
}
