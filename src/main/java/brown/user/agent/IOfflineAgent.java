package brown.user.agent;

import brown.communication.messages.IServerToAgentMessage;

public interface IOfflineAgent extends IAgent {
   
  public void receiveMessage(IServerToAgentMessage message); 
  
}
