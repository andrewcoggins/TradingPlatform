package brown.mechanism.messages.library;

import brown.user.agent.library.AbsAgent;

/**
 * Private information messages provide agents with some private
 * information. Abstract and varies by auction. 
 * @author andrew
 *
 */
public abstract class PrivateInformationMessage extends AbsMessage {
  
  /**
   * All private information messages have a message ID. 
   * @param ID
   */
  public PrivateInformationMessage(Integer ID) {
    super(ID);
  }
  
  public void dispatch(AbsAgent agent) {
    agent.onPrivateInformation(this);
  }
}
