package brown.agent.library;

import brown.channels.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.BankUpdate;
import brown.setup.Logging;
import brown.setup.Setup;
import brown.setup.library.SimpleSetup;

/**
 * Agent for students to implement the lemonade game. Underlying methods implemented 
 * @author andrew
 *
 */
public class LemonadeAgent extends LemonadeAbstractAgent {
  
  private int posn;
  
  public LemonadeAgent(String host, int port, int position)
      throws AgentCreationException {
    super(host, port, new SimpleSetup());
    this.posn = position; 
    // TODO Auto-generated constructor stub
  } 
  
  public void onLemonade(LemonadeChannel channel) {
    //enter a position between 0 and 11 inclusive.

    channel.bid(this, this.posn);
  }
  
  @Override
  public void onBankUpdate(BankUpdate bankUpdate) {
    // TODO Auto-generated method stub
    Logging.log("[Bank update]Agent with position " + this.posn + ": " + (bankUpdate.newAccount.monies - bankUpdate.oldAccount.monies)); 
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new LemonadeAgent("localhost", 2121, 1);
    new LemonadeAgent("localhost", 2121, 1);
    new LemonadeAgent("localhost", 2121, 1);
    while(true){}
  }
  
}