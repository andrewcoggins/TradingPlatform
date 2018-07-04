package brown.messages.library;

import brown.agent.AbsAgent;

/**
 * message provided to agent when there is a server error 
 * caused by a message an agent sent
 * @author andrew
 */
public class ErrorMessage extends AbsMessage {
	
	public final String error;
  
	/**
	 * void kryo
	 */
  public ErrorMessage() {
		super(null);
		error= null;
	}
	
	/**
	 * Rejection for registration
	 * @param ID - agentID
	 * @param error - error message
	 */
	public ErrorMessage(Integer ID, String error) {
		super(ID);
		this.error = error;
		}
	
	@Override
	public void dispatch(AbsAgent agent) {
		agent.onErrorMessage(this);
	}

}
