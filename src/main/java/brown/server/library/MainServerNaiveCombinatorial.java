package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.ComSecondPriceRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.AbsValueConfig;
import brown.value.config.ComplexConfig;

/*
 * Use this class to run the server side of your game.
 * just edit the rules to the game that you'd like to play.
 * 
 * Complex Simultaneous Second-price auction
 * 
 */
public class MainServerNaiveCombinatorial {
  
  public static void main(String[] args) throws InterruptedException {
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    List<AbsValueConfig> allValInfo = new ArrayList<AbsValueConfig>();
    // our valuation information and rules information.
    Set<MultiTradeable> goods = new HashSet<MultiTradeable>();
    goods.add(new MultiTradeable(0));
    goods.add(new MultiTradeable(1));
    allValInfo.add(new ComplexConfig(goods));
    allMarkets.add(new ComSecondPriceRules()); 
    new RunServer(2121, new LemonadeSetup()).runGame(allMarkets, allValInfo, 1000.0, null);
  }
}