package brown.agent.library;

import brown.channels.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.setup.Setup;

/**
 * Agent for students to implement the lemonade game. Underlying methods implemented 
 * @author andrew
 *
 */
public class LemonadeAgent extends LemonadeAbstractAgent {

  public LemonadeAgent(String host, int port, Setup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  } 
  
  public void onLemonade(LemonadeChannel channel) {
    //enter a position between 0 and 11 inclusive.
    Integer position = -1; 
    channel.bid(this, position);
  }
  
}