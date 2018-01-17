package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.ComSecondPriceRules;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Good;
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
    Set<Good> goods = new HashSet<Good>();
    goods.add(new Good(0));
    goods.add(new Good(1));
    allValInfo.add(new ComplexConfig(goods));
    allMarkets.add(new ComSecondPriceRules()); 
    new RunServer(2121, new SimpleSetup()).runGame(allMarkets, allValInfo, 1000.0, null);
  }
}