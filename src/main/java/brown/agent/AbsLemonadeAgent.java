package brown.agent;

import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

/**
 * Abstract agent for the lemonade game.
 * All lemonade agents will extend this class.
 * @author andrew
 *
 */
public abstract class AbsLemonadeAgent extends AbsAgent implements ILemonadeAgent{

  public AbsLemonadeAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  public abstract void onLemonade(LemonadeChannel channel);
}