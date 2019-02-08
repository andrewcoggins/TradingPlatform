package brown.communication.messages.library; 

import brown.communication.messages.IMessage;
import brown.user.agent.library.AbsAgent;

/**
 * a message is used to communicate between the agent and the server
 * @author lcamery
 */
public abstract class AbsMessage implements IMessage {
  
	protected final Integer ID;
	
	/**
	 * Empty message
	 * @param ID - message ID
	 */
	public AbsMessage(Integer ID) {
		this.ID = ID;
	}
	
	public Integer getID() {
		return this.ID;
	}
	
	public abstract void dispatch(AbsAgent agent);
	
}
