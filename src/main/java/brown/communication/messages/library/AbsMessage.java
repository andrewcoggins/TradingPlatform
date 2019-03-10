package brown.communication.messages.library; 


import brown.communication.messages.IMessage;

/**
 * a message is used to communicate between the agent and the server
 * @author lcamery
 */
public abstract class AbsMessage implements IMessage {
  
	protected final Integer messageID;
	
	/**
	 * Empty message
	 * @param ID - message ID
	 */
	public AbsMessage(Integer messageID) {
		this.messageID = messageID;
	}
	
	public Integer getMessageID() {
		return this.messageID;
	}

	
}
