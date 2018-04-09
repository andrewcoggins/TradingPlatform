package brown.agent;

import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

/**
 * abstract class for open outcry auction games. 
 * All open outcry agents will implement this class.
 * @author andrew
 *
 */
public abstract class AbsOpenOutcryAgent extends AbsAuctionAgent implements IOpenOutcryAgent {

  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup, String name)
      throws AgentCreationException {
    super(host, port, gameSetup, name);
  } 
}
