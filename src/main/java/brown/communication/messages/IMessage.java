package brown.communication.messages;

/**
 * A message is used to communicate between the agent and the server
 * @author acoggins
 *
 */
public interface IMessage {
	
  /**
   * Get message ID
   * @return ID
   */
   public Integer getMessageID();
   
   public Integer getAgentID();
	
}
