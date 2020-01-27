package brown.communication.messages;

public interface IRegistrationResponseMessage extends IServerToAgentMessage {

  public Integer getPublicAgentID(); 
  
  public String getName(); 
  
  
}
