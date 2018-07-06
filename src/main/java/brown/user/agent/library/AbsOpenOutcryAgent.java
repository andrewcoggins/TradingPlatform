package brown.user.agent.library;

import brown.system.setup.ISetup;
import brown.user.agent.IOpenOutcryAgent;

/**
 * abstract class for open outcry auction games. 
 * All open outcry agents will implement this class.
 * @author andrew
 *
 */
public abstract class AbsOpenOutcryAgent extends AbsAuctionAgent implements IOpenOutcryAgent {

  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }
  
  public AbsOpenOutcryAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup, name);
  } 
}
