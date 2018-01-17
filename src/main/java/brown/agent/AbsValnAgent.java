package brown.agent;

import brown.value.valuation.IValuation;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsValnAgent extends AbsAgent implements IAgent { 

  private IValuation privateValuation; 
  
  /**
   * Implementations should always invoke super()
   * 
   * @param host
   * @param port
   * @param gameSetup
   * @throws AgentCreationException
   */
  public AbsValnAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);//??
  }
  
}