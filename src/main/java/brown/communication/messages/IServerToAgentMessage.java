package brown.communication.messages;

import brown.user.agent.IAgentBackend;

public interface IServerToAgentMessage extends IMessage {

  /**
   * Figures out what type of message this is, and acts accordingly
   * @param agent
   */
  public void agentDispatch(IAgentBackend agent);
  
}
