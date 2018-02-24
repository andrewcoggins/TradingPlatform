package brown.messages.library;

import brown.agent.AbsAgent;

public abstract class PrivateInformationMessage extends AbsMessage {
  
  public PrivateInformationMessage(Integer ID) {
    super(ID);
  }

  public void dispatch(AbsAgent agent) {
    agent.onPrivateInformation(this);
  }
}
