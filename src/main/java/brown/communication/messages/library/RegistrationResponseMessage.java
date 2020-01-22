package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.user.agent.IAgent;

public class RegistrationResponseMessage extends AbsServerToAgentMessage implements IRegistrationResponseMessage {

  private String name; 
  private Integer publicAgentID; 
  
 
  public RegistrationResponseMessage() {
    super(null, null); 
    this.name = null; 
  }
  
  public RegistrationResponseMessage(Integer messageID, Integer agentID, Integer publicAgentID, String name) {
    super(messageID, agentID);
    this.publicAgentID = publicAgentID; 
    this.name = name; 
  }

  public void agentDispatch(IAgent agent) {
    agent.onRegistrationResponse(this);
  }
  
  @Override
  public String getName() {
    return this.name; 
  }

  @Override
  public Integer getPublicAgentID() {
    return this.publicAgentID;
  }

  @Override
  public String toString() {
    return "RegistrationResponseMessage [name=" + name + ", publicAgentID="
        + publicAgentID + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result
        + ((publicAgentID == null) ? 0 : publicAgentID.hashCode());
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
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (publicAgentID == null) {
      if (other.publicAgentID != null)
        return false;
    } else if (!publicAgentID.equals(other.publicAgentID))
      return false;
    return true;
  }
  
  
}
