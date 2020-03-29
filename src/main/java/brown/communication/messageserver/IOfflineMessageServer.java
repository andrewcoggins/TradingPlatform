package brown.communication.messageserver;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.user.agent.IAgentBackend;

public interface IOfflineMessageServer {

  // server receives registration message from agent.
  public void onRegistration(IAgentBackend connection,
      IRegistrationMessage registrationMessage);

  // server receives bid message from agent.
  public void onBid(IAgentBackend connection, ITradeMessage bidMessage);

  // server sends message to agent.
  public void sendMessage(Integer agentPrivateID,
      IServerToAgentMessage message, boolean wait);
  
  public void receiveMessage(IAgentBackend connection, IAgentToServerMessage message);
  
  public boolean ready();

  public void stopMessageServer();
}
