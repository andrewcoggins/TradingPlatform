package brown.server.library; 

import java.util.ArrayList;
import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SimSecondPriceRules;
import brown.setup.library.SimpleSetup;

/*
 * Use this class to run the server side of your game.
 * just edit the rules to the game that you'd like to play.
 * 
 * hmm... what to do about special registrations?
 */
public class MainServer {
  
  public static void main(String[] args) throws InterruptedException {
    //for now just gonna build this where you input things into this file.
    //But later on i'd like to use command line input.
    List<AbsMarketPreset> allMarkets = new ArrayList<AbsMarketPreset>();
    //add whatever you want to do.
    allMarkets.add(new SimSecondPriceRules()); 
    new RunServer(2121, new SimpleSetup()).runGame(allMarkets, null);
  }
}