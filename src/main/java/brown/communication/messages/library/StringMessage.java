package brown.communication.messages.library;

import brown.user.agent.library.AbsAgent;

/**
 * message provided to agent when there is a server error 
 * caused by a message an agent sent
 * @author andrew
 */
public class StringMessage extends AbsMessage {
  
  public final String message;
  
  /**
   * void kryo
   */
  public StringMessage() {
    super(null);
    this.message= null;
  }
  
  /**
   * Rejection for registration
   * @param ID - agentID
   * @param error - error message
   */
  public StringMessage(Integer ID, String message) {
    super(ID);
    this.message = message;
    }
  
  @Override
  public void dispatch(AbsAgent agent) {
    agent.onStringMessage(this);
  }

}
