package brown.user.agent.library;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import brown.logging.library.Logging;
import brown.mechanism.bidbundle.library.GameBidBundle;
import brown.mechanism.channel.library.GameChannel;
import brown.mechanism.messages.library.GameReportMessage;
import brown.mechanism.messages.library.LemonadeReportMessage;
import brown.mechanism.messages.library.PrivateInformationMessage;
import brown.system.setup.library.LemonadeSetup;

/**
 * Agent for the lemonade game.
 * @author andrew
 */
public class LemonadeAgent extends AbsLemonadeAgent {

  private int posn;
  private int NUM_SLOTS = 12;
  private int[] positions = new int[NUM_SLOTS];
  @SuppressWarnings("unchecked")
  private List<Integer>[] positions_ids = (List<Integer>[]) new List[NUM_SLOTS];
  
  public LemonadeAgent(String host, int port, int position)
       {
    super(host, port, new LemonadeSetup());
    this.posn = position; 
    for(int i = 0; i < NUM_SLOTS; i++) {
      positions[i] = 0; 
      positions_ids[i] = new LinkedList<Integer>();
    }
  } 
  
  public void onLemonade(GameChannel channel) {
    // Enter a position between 0 and NUM_SLOTS-1 inclusive.
    channel.bid(this, new GameBidBundle(this.posn));
  }

  // Mess with logging here to check if it works, but be warned this will flood your console with high # of agents
  @Override
  public void onGameReport(GameReportMessage gameReport) {
    super.onGameReport(gameReport);
    LemonadeReportMessage lemonadeUpdate = this.latestGameReport;
    for (int i = 0; i < NUM_SLOTS; i++) {
      this.positions[i] = lemonadeUpdate.getCount(i);
      if (!lemonadeUpdate.isAnon()){
        this.positions_ids[i] = lemonadeUpdate.getIDs(i);
        Logging.log("Cumulative Results IDS:" + Arrays.toString(this.positions_ids));                
      }
    }    
    // Logging.log("Cumulative Results:" + Arrays.toString(this.positions));      
    Logging.log("Total Monies: " + this.monies);
    }
  
  public static void main(String[] args)  {
    new LemonadeAgent("localhost", 2121, 1);
    new LemonadeAgent("localhost", 2121, 1);
    new LemonadeAgent("localhost", 2121, 6);   
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);       
    new LemonadeAgent("localhost", 2121, 1);           
    while(true){}
  }

  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {
    // TODO Auto-generated method stub
    
  }
}