package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.user.agent.IAgent;

public class RegistrationResponseMessage extends AbsServerToAgentMessage implements IRegistrationResponseMessage {

  private Integer agentID; 
  private String name; 
 
  public RegistrationResponseMessage(Integer messageID, Integer agentID, String name) {
    super(messageID);
    this.agentID = agentID; 
    this.name = name; 
  }

  public void agentDispatch(IAgent agent) {
    
  }
  
  public Integer getAgentID() {
    return this.agentID; 
  }
  
  public String getName() {
    return this.name; 
  }

  @Override
  public String toString() {
    return "RegistrationResponseMessage [agentID=" + agentID + ", name=" + name
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agentID == null) ? 0 : agentID.hashCode());
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
    RegistrationResponseMessage other = (RegistrationResponseMessage) obj;
    if (agentID == null) {
      if (other.agentID != null)
        return false;
    } else if (!agentID.equals(other.agentID))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
}
