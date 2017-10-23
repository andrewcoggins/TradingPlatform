package brown.agent;


import brown.exceptions.AgentCreationException;
import brown.setup.Setup;
/**
 * All bidding agents will implement this class It abstracts away the
 * communication issues and lets authors focus on writing bidding logic.
 * 
 * @author lcamery
 */
public abstract class Agent extends AClient{

	
  public Agent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }


  
}
