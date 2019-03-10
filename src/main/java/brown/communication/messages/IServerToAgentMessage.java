package brown.communication.messages;

import brown.user.agent.IAgent;

public interface IServerToAgentMessage extends IMessage {

  /**
   * Figures out what type of message this is, and acts accordingly
   * @param agent
   */
  public void agentDispatch(IAgent agent);
  
}
