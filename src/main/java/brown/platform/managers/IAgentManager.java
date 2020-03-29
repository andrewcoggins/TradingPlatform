package brown.platform.managers;

import brown.communication.messageserver.IOfflineMessageServer;
import brown.user.main.IAgentConfig;

/**
 * Creates and stores offline agents. 
 * @author andrewcoggins
 *
 */
public interface IAgentManager {
  
  /**
   * create an agent. 
   * @param agentConfig
   * config of agent to be created. 
   */
  public void createAgent(IAgentConfig agentConfig); 
  
  /**
   * Start all the agents. 
   * @param messageServer
   */
  public void startAgents(IOfflineMessageServer messageServer);
  
  /**
   * lock the agent manager to deny agent creation. 
   */
  public void lock(); 

  
}
