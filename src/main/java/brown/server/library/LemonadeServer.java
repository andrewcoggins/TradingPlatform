package brown.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.preset.library.LemonadeAnonRules;
import brown.market.preset.library.LemonadeNonAnonRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.LemonadeConfig;

public class LemonadeServer {
  public static void main(String[] args) throws InterruptedException {
    // Make sure students know this before hand so they can change some constant in their agents or something
    int numSlots = 50;
    
    // Using 2 * number of slots glasses of lemonade right now, this can be changed to 
    // whatever, but just know that lower numbers might cause problems bc glasses of 
    // lemonade aren't divisible
    List<ITradeable> allTradeables = new LinkedList<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, numSlots*2));
       
    // LemonadeAnon and LemonadeNonAnon
    new RunServer(2121, new LemonadeSetup()).runSimpleSim(allTradeables, new LemonadeAnonRules(numSlots), 
        new LemonadeConfig(), 0., new LinkedList<ITradeable>());;
  }
}
