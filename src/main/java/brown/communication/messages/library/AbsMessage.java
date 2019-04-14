package brown.communication.messages.library; 


import brown.communication.messages.IMessage;

/**
 * a message is used to communicate between the agent and the server
 * @author lcamery
 */
public abstract class AbsMessage implements IMessage {
  
	protected final Integer messageID;
	protected final Integer agentID; 
	
	/**
	 * Empty message
	 * @param ID - message ID
	 */
	public AbsMessage(Integer messageID, Integer agentID) {
		this.messageID = messageID;
		this.agentID = agentID; 
	}
	
	public Integer getMessageID() {
		return this.messageID;
	}

	public Integer getAgentID() {
    return this.agentID; 
  }
	
}
