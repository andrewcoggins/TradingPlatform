package brown.server.library; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.CombinatorialRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;
import brown.value.config.ComplexConfig;

/**
 * Use this class to run the server side of your game.
 * just edit the rules to the game that you'd like to play.
 * 
 * Complex Simultaneous Second-price auction
 * 
 */
public class CombinatorialServer {
  
  public static void main(String[] args) throws InterruptedException {
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    List<ValConfig> allValInfo = new ArrayList<ValConfig>();
    
    //create tradeables
    Set<ITradeable> goods = new HashSet<ITradeable>();
//    goods.add(new ComplexTradeable(0));
//    goods.add(new ComplexTradeable(1));
    
    //valuations and rules
    allValInfo.add(new ComplexConfig(goods));
    allMarkets.add(new CombinatorialRules()); 
    
    new RunServer(2121, new LemonadeSetup()).runGame(allMarkets, allValInfo, 1000.0, null);
  }
}