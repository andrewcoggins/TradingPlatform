package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import brown.user.main.IAgentConfig;

public class AgentConfigTest {

  @Test
  public void testAgentConfig() {
    IAgentConfig aConfig = new AgentConfig("agentclass", "agentName"); 
    
    IAgentConfig aConfigTwo = new AgentConfig("agentclass", "agentName"); 
    
    assertEquals(aConfig, aConfigTwo); 
    
    
    assertEquals(aConfig.getAgentClass(), "agentclass"); 
    assertEquals(aConfig.getAgentName(), "agentName"); 
    
  }
  
  
}
