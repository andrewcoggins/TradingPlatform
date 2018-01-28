package brown.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.preset.library.LemonadeAnonRules;
import brown.market.preset.library.LemonadeFloatRules;
import brown.market.preset.library.LemonadeGroupedRulesAnon;
import brown.market.preset.library.LemonadeGroupedRulesNotAnon;
import brown.market.preset.library.LemonadeNonAnonRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.LemonadeConfig;

public class LemonadeServer {
  public static void main(String[] args) throws InterruptedException {
    // Make sure students know this before hand so they can change some constant in their agents or something
    int numSlots = 12;
    // This is for the 3 person case – the rules will automatically normalize for larger groups
    int totalTradeables = 24;
    
    // simulation variables
    int delayTime = 5;
    int lag = 50; // speed at which rounds run - at lag=100, 100 trials takes 50s-60s
    int numRuns = 50;
    
    List<ITradeable> allTradeables = new LinkedList<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, totalTradeables));
       
    // LemonadeGroupedRulesAnon and LemonadeGroupedRulesNotAnon
    new RunServer(2121, new LemonadeSetup()).runSimpleSim(allTradeables, new LemonadeGroupedRulesNotAnon(numSlots,numRuns), 
        new LemonadeConfig(), 0., new LinkedList<ITradeable>(), delayTime, lag);
  }
}
