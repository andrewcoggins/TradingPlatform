package brown.user.main.library;

import brown.user.main.IAgentConfig;

public class AgentConfig implements IAgentConfig {
  
  private String agentClass;
  private String agentName; 

  
  public AgentConfig(String agentClass, String agentName) {
     this.agentName = agentName; 
     this.agentClass = agentClass; 

  }
  
  @Override
  public String getAgentClass() {
    return this.agentClass;
  }

  @Override
  public String getAgentName() {
    return this.agentName;
  }

  @Override
  public String toString() {
    return "AgentConfig [agentClass=" + agentClass + ", agentName=" + agentName
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((agentClass == null) ? 0 : agentClass.hashCode());
    result = prime * result + ((agentName == null) ? 0 : agentName.hashCode());
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
    AgentConfig other = (AgentConfig) obj;
    if (agentClass == null) {
      if (other.agentClass != null)
        return false;
    } else if (!agentClass.equals(other.agentClass))
      return false;
    if (agentName == null) {
      if (other.agentName != null)
        return false;
    } else if (!agentName.equals(other.agentName))
      return false;
    return true;
  }

  

}
