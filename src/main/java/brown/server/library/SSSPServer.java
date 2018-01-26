package brown.server.library; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.library.SSSPRules;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SSSPConfig;

/**
 * Use this class to run the server side of your game.
 * Just edit the rules to the game that you'd like to play.
 * 
 * This is the only file the server-side user should have to edit.
 * 
 * hmm... what to do about special registrations?
 * repeated registrations?
 */
public class SSSPServer {

  public static void main(String[] args) throws InterruptedException {
    // Create _ tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    int numTradeables = 5;
    int delay = 10;
    int i = 0;
    while (i<numTradeables){
      SimpleTradeable toAdd = new SimpleTradeable(i);            
      allTradeables.add(toAdd);
      allTradeablesList.add(toAdd);
      i++;
    }

    new RunServer(2121, new SSSPSetup()).runSimpleSim(allTradeablesList, new SSSPRules(5), 
        new SSSPConfig(allTradeables), 100., new LinkedList<ITradeable>(),delay, 1000);;
  }
}