package brown.agent.library;

import brown.agent.AbsLemonadeAgent;
import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
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
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO Auto-generated method 
    if (marketUpdate instanceof LemonadeReportMessage) { 
      LemonadeReportMessage lemonadeUpdate = (LemonadeReportMessage) marketUpdate;
      for (int i = 0; i < 12; i++) {
        this.positions[i] = this.positions[i] + lemonadeUpdate.getCount(i);
      }
      printIsland();
    }
    else {
      System.out.println("ERROR: Lemonade Report Not Received");
    }
  }
  
  @Override
  public void onBankUpdate(BankUpdateMessage bankUpdate) {
    // TODO Auto-generated method stub
    //Logging.log("[Bank update]Agent with position " + this.posn + ": " + (bankUpdate.newAccount.monies - bankUpdate.oldAccount.monies + ", Total Money: " + bankUpdate.newAccount.monies)); 
  }
  
  //prints the island where there are 12 spaces.
  private synchronized void printIsland() {
    System.out.println("Lemonade Arrangement:");
    for(int i = 0; i < 4; i++) {
      printNumber(i);
    }
    System.out.print('\n');
    printNumber(11); 
    System.out.print("            ");
    printNumber(4); 
    System.out.print('\n');
    printNumber(10); 
    System.out.print("            ");
    printNumber(5); 
    System.out.print('\n');
    for(int i = 9; i > 5; i--) {
      printNumber(i);
    }
    System.out.print('\n');
  }
  
  private void printNumber(Integer aNum) { 
    if (Math.abs(positions[aNum]) < 10) { 
      System.out.print("| " + positions[aNum] + " | ");
    } else {
      System.out.print("| " + positions[aNum] + "| ");
    }
  }
  
  public static void main(String[] args) throws AgentCreationException {
    new LemonadeAgent("localhost", 2121, 2);
    new LemonadeAgent("localhost", 2121, 4);
    new LemonadeAgent("localhost", 2121, 9);
    while(true){}
  }
  
}