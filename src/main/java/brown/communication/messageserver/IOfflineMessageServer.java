package brown.communication.messageserver;

import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.user.agent.IAgent;

public interface IOfflineMessageServer {

  // server receives registration message from agent.
  public void onRegistration(IAgent connection,
      IRegistrationMessage registrationMessage);

  // server receives bid message from agent.
  public void onBid(IAgent connection, ITradeMessage bidMessage);

  // server sends message to agent.
  public void sendMessage(Integer agentPrivateID,
      IServerToAgentMessage message);

  public void stopMessageServer();

  public void waitForRegistrations();
  
  public void waitForBids(String messageType);
  
  public void notifyToRespond(); 

  public boolean readyToNotify(); 
  
  public void clearMessagesRecieved(); 
}
