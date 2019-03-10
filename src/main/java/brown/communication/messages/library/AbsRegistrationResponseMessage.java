package brown.communication.messages.library;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.user.agent.IAgent;

public abstract class AbsRegistrationResponseMessage extends AbsServerToAgentMessage implements IRegistrationResponseMessage {

  private Integer agentID; 
  private String name; 
 
  public AbsRegistrationResponseMessage(Integer messageID, Integer agentID, String name) {
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
  
}
