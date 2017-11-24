package brown.messages;

import brown.agent.AbsAgent;

/**
 * a message communicates between the agent and the server.
 * @author lcamery
 */
public abstract class AbsMessage {
	protected final Integer ID;
	
	/**
	 * Empty message
	 * @param ID : message ID
	 */
	public AbsMessage(Integer ID) {
		this.ID = ID;
	}
	
	/**
	 * Get message ID
	 * @return ID
	 */
	public Integer getID() {
		return this.ID;
	}
	
	/**
	 * Tells agent what type of message we are
	 * @param agent
	 */
	public abstract void dispatch(AbsAgent agent);
}
