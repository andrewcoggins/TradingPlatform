package brown.agent.library;

import brown.agent.AbsLemonadeAgent;
import brown.bidbundle.library.GameBidBundle;
import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.setup.library.LemonadeSetup;
import brown.setup.Logging;

/**
 * Agent for the lemonade game.
 * @author andrew
 */
public class ExampleLemonadeAgent extends AbsLemonadeAgent {
  
  public ExampleLemonadeAgent(String host, int port, int position)
      throws AgentCreationException {
    super(host, port, new LemonadeSetup());
    // What fields might you want to create here to store data?
  } 
  
  public void onLemonade(LemonadeChannel channel) {
    // Enter a position between 0 and NUM_SLOTS-1 inclusive.
    channel.bid(this, new GameBidBundle(1)); // e.g. bid 1
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    super.onGameReport(gameReport);
    LemonadeReportMessage lemonadeUpdate = this.latestGameReport; // this is a report
    lemonadeUpdate.isAnon(); // is this report anonymous        
    lemonadeUpdate.getCount(1); // how many people were in the 1 slot last game (always contained in report)
    lemonadeUpdate.getIDs(1); // which people were in the 1 slot last game (only contained if report is not-anonymous)

    Logging.log("Total Monies: " + this.monies); //  Print my money
    }
  
  public static void main(String[] args) throws AgentCreationException {
    new ExampleLemonadeAgent("localhost", 2121, 1);
    while(true){}
  }  
  
}