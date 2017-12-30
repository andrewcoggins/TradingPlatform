package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.LemonadeRules;
import brown.setup.library.SimpleSetup;
import brown.value.config.AbsValueConfig;
import brown.value.config.NullConfig;

/*
 * Use this class to run the server side of your game.
 * just edit the rules to the game that you'd like to play.
 * 
 * Lemonade Game
 * 
 */
public class MainServerLemonade {
  
  public static void main(String[] args) throws InterruptedException {
    // for now just gonna build this where you input things into this file.
    // But later on i'd like to use command line input.
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    List<AbsValueConfig> allValInfo = new ArrayList<AbsValueConfig>();
    // our valuation information and rules information.
    allValInfo.add(new NullConfig());
    allMarkets.add(new LemonadeRules()); 
    new RunServer(2121, new SimpleSetup()).runGame(allMarkets, allValInfo);
  }
}