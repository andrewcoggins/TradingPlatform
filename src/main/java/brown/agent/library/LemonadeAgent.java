package brown.agent.library;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import brown.agent.AbsLemonadeAgent;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.setup.library.LemonadeSetup;
import brown.setup.Logging;

/**
 * Agent for the lemonade game.
 * @author andrew
 */
public class LemonadeAgent extends AbsLemonadeAgent {

  private int posn;
  private int NUM_SLOTS = 50;
  private int[] positions = new int[NUM_SLOTS];
  @SuppressWarnings("unchecked")
  private List<Integer>[] positions_ids = (List<Integer>[]) new List[NUM_SLOTS];
  
  public LemonadeAgent(String host, int port, int position)
      throws AgentCreationException {
    super(host, port, new LemonadeSetup());
    this.posn = position; 
    for(int i = 0; i < NUM_SLOTS; i++) {
      positions[i] = 0; 
      positions_ids[i] = new LinkedList<Integer>();
    }
  } 
  
  public void onLemonade(LemonadeChannel channel) {
    // Enter a position between 0 and NUM_SLOTS-1 inclusive.
    channel.bid(this, new GameBidBundle(this.posn));
  }
  
  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    super.onBankUpdate(bankUpdate);
    // Logging.log("[Bank update]Agent with position " + this.posn + ": " + (bankUpdate.moniesChanged)); 
  }
  
  @Override
  public void onGameReport(GameReportMessage marketUpdate) {
    if (marketUpdate instanceof LemonadeReportMessage) { 
      LemonadeReportMessage lemonadeUpdate = (LemonadeReportMessage) marketUpdate;
      for (int i = 0; i < NUM_SLOTS; i++) {
        this.positions[i] = lemonadeUpdate.getCount(i);
        if (!lemonadeUpdate.isAnon()){
          this.positions_ids[i] = lemonadeUpdate.getIDs(i);
          Logging.log("Cumulative Results IDS:" + Arrays.toString(this.positions_ids));                
        }
      }
      Logging.log("Cumulative Results:" + Arrays.toString(this.positions));      
      Logging.log("Total Monies: " + this.monies);
    }
    else {
      System.out.println("ERROR: Lemonade Report Not Received");
    }
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new LemonadeAgent("localhost", 2121, 1);
    new LemonadeAgent("localhost", 2121, 25);
    new LemonadeAgent("localhost", 2121, 30);
    
    while(true){}
  }  
}