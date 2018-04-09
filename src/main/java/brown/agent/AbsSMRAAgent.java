package brown.agent; 

import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

/**
 * abstract class for SMRAAgents. These agents will participate in an 
 * open-outcry discovery auction before participating in a simple sealed
 * auction.
 * @author acoggins
 *
 */
public abstract class AbsSMRAAgent extends AbsOpenOutcryAgent {

  public AbsSMRAAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }


  //TODO: do some nice things here.
}