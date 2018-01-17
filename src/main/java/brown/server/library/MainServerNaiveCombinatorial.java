package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.ComSecondPriceRules;
import brown.setup.library.SimpleSetup;
import brown.tradeable.library.Tradeable;
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
    Set<Tradeable> goods = new HashSet<Tradeable>();
    goods.add(new Tradeable(0));
    goods.add(new Tradeable(1));
    allValInfo.add(new ComplexConfig(goods));
    allMarkets.add(new ComSecondPriceRules()); 
    new RunServer(2121, new SimpleSetup()).runGame(allMarkets, allValInfo, 1000.0, null);
  }
}