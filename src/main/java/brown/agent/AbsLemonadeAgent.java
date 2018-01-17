package brown.agent;

import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.ISetup;

public abstract class AbsLemonadeAgent extends AbsAgent {

  public AbsLemonadeAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  /**
   * Provides agent response in lemonade game
   * @param simpleWrapper - lemonade channel
   */
  public abstract void onLemonade(LemonadeChannel channel);

}