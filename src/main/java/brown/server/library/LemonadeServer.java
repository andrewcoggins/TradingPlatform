package brown.server.library; 

import java.util.ArrayList;
import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.LemonadeRules;
import brown.setup.library.LemonadeSetup;
import brown.value.config.ValConfig;
import brown.value.config.NullConfig;

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
    // our valuation information and rules information.
    allValInfo.add(new NullConfig());
    allMarkets.add(new LemonadeRules()); 
    new RunServer(2121, new LemonadeSetup()).runGame(allMarkets, allValInfo, null, null);
  }
  
}