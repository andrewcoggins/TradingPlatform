package brown.agent;

import brown.value.valuation.IValuation;
import brown.exceptions.AgentCreationException;
import brown.messages.library.PrivateInformationMessage;
import brown.setup.ISetup;

//this class doesn't appear to be used yet.
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
  
  public abstract void onPrivateInformation(PrivateInformationMessage privateInfo);
  
}