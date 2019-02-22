package brown.communication.messages.library;

import brown.communication.messages.IRegistrationMessage;
import brown.user.agent.library.AbsAgent;

public abstract class AbsRegistrationMessage extends AbsMessage implements IRegistrationMessage {

  private String name; 
  
  public AbsRegistrationMessage() {
    super(null);
  }
  
  public AbsRegistrationMessage(Integer messageID) {
    super(messageID);
    this.name = ""; 
  }
  
  public AbsRegistrationMessage(Integer messageID, String name) {
    super(messageID); 
    this.name = name; 
  }
  
  @Override
  public void dispatch(AbsAgent agent) {
    agent.onRegistration(this);
  }
  
  @Override
  public String getName() {
    return this.name; 
  }
  
  @Override
  public String toString() {
    return "AbsRegistrationMessage [name=" + name + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbsRegistrationMessage other = (AbsRegistrationMessage) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
}
