package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SimSPRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.ValConfig;
import brown.value.config.SimpleConfig;

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
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    ValConfig allValInfo = new ArrayList<ValConfig>();
    
    //create tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    for (int i = 0; i < 3; i++) {
      allTradeables.add(new MultiTradeable(i, 1)); //just one copy of each good for now
    }
    
    //valuations and rules
    allValInfo.add(new SimpleConfig(allTradeables));
    allMarkets.add(new SimSPRules());
    
    new RunServer(2121, new LemonadeSetup()).runGame(allTradeables, allMarkets, allValInfo,
        1000.0, new LinkedList<ITradeable>());
  }
}