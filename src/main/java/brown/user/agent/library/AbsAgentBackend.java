package brown.user.agent.library;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.logging.library.SystemLogging;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;

public abstract class AbsAgentBackend implements IAgentBackend {
  
  protected final IAgent agent; 
  public Integer ID;
  public Integer publicID;
  public String simulationJsonFileName; 
  
  public AbsAgentBackend(IAgent agent) {
    this.agent = agent; 
    this.ID = -1; 
    this.publicID = -1; 
    this.simulationJsonFileName = ""; 
  }
  
  @Override
  public Integer getPublicID() {
    return this.publicID; 
  }
  
  @Override
  public Integer getPrivateID() {
    return this.ID; 
  }
  

  
  @Override
  public void
      onRegistrationResponse(IRegistrationResponseMessage registrationMessage) {
    SystemLogging.log("[-] Registered To Server");
    this.ID = registrationMessage.getAgentID(); 
    this.publicID = registrationMessage.getPublicAgentID(); 
    this.simulationJsonFileName = registrationMessage.getSimulationJsonFileName(); 
    SystemLogging.log("Private ID: " + this.ID);
    SystemLogging.log("Public ID: " + this.publicID);
    SystemLogging.log("simulation JSON filename: " + this.simulationJsonFileName);
  }
  

  @Override
  public abstract void receiveMessage(IServerToAgentMessage message); 
  
  @Override
  public abstract void sendMessage(IAgentToServerMessage message);
  
}
