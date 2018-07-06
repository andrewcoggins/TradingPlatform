package brown.user.server;

import java.util.LinkedList;
import java.util.List;

import brown.auction.preset.LemonadeGroupedRulesNotAnon;
import brown.auction.value.config.library.LemonadeConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.MultiTradeable;
import brown.platform.server.library.RunServer;
import brown.system.setup.library.LemonadeSetup;

/**
 * Server main class for lemonade game.
 * @author acoggins
 *
 */
public class LemonadeServer {
  public static void main(String[] args) throws InterruptedException {
    // Make sure students know this before hand so they can change some constant in their agents or something
    int numSlots = 12;
    // This is for the 3 person case – the rules will automatically normalize for larger groups
    int totalTradeables = 24;
    
    // simulation variables
    int delayTime = 5;
    int lag = 50; // speed at which rounds run - at lag=100, 100 trials takes 50s-60s
    int numRuns = 5;
    
    List<ITradeable> allTradeables = new LinkedList<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, totalTradeables));
       
    // LemonadeGroupedRulesAnon and LemonadeGroupedRulesNotAnon
    new RunServer(2121, new LemonadeSetup()).runSimpleSim(allTradeables, new LemonadeGroupedRulesNotAnon(numSlots,numRuns), 
        new LemonadeConfig(), 0., new LinkedList<ITradeable>(), delayTime, lag, null);
  }
}
