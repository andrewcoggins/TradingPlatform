package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.LemonadeRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.ValConfig;
import brown.value.config.LemonadeConfig;

/**
 * Use this class to run the server side of your game.
 * Just edit the rules to the game that you'd like to play.
 * 
 * Lemonade Game
 * 
 */
public class LemonadeServer {
  
  public static void main(String[] args) throws InterruptedException {
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    List<ValConfig> allValInfo = new ArrayList<ValConfig>();
   
    //create twelve copies of one tradeable
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, 24));
    
    // our valuation information and rules information.
    allValInfo.add(new LemonadeConfig());
    allMarkets.add(new LemonadeRules()); 
    
    new RunServer(2121, new LemonadeSetup()).runGame(allTradeables, allMarkets, allValInfo, null, null);
  }
  
}