package brown.agent.library;

import brown.agent.AbsLemonadeAgent;
import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdate;
import brown.messages.library.GameReport;
import brown.messages.library.LemonadeReport;
import brown.setup.Logging;
import brown.setup.library.SimpleSetup;

/**
 * Agent for students to implement the lemonade game. Underlying methods implemented 
 * @author andrew
 *
 */
public class LemonadeAgent extends AbsLemonadeAgent {
  
  private int posn;
  private int[] positions = new int[12];
  
  public LemonadeAgent(String host, int port, int position)
      throws AgentCreationException {
    super(host, port, new SimpleSetup());
    this.posn = position; 
    for(int i = 0; i < 12; i++) {
      positions[i] = 0; 
    }
  } 
  
  public void onLemonade(LemonadeChannel channel) {
    //enter a position between 0 and 11 inclusive.
    channel.bid(this, this.posn);
  }
  
  @Override
  public void onMarketUpdate(GameReport marketUpdate) {
    // TODO Auto-generated method 
    if (marketUpdate instanceof LemonadeReport) { 
      LemonadeReport lemonadeUpdate = (LemonadeReport) marketUpdate;
      for (int i = 0; i < 12; i++) {
        this.positions[i] = this.positions[i] + lemonadeUpdate.getCount(i);
      }
      System.out.println("Game Report Received");
    }
    else{
      System.out.println("ERROR: Lemonade Report Not Received");
    }
  }
  
  @Override
  public void onBankUpdate(BankUpdate bankUpdate) {
    // TODO Auto-generated method stub
    //Logging.log("[Bank update]Agent with position " + this.posn + ": " + (bankUpdate.newAccount.monies - bankUpdate.oldAccount.monies + ", Total Money: " + bankUpdate.newAccount.monies)); 
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new LemonadeAgent("localhost", 2121, 2);
    new LemonadeAgent("localhost", 2121, 4);
    new LemonadeAgent("localhost", 2121, 9);
    while(true){}
  }
  
}