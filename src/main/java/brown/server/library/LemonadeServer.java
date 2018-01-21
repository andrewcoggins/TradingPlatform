package brown.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.preset.library.LemonadeRules;
import brown.setup.library.LemonadeSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.value.config.LemonadeConfig;

public class LemonadeServer2 {
  public static void main(String[] args) throws InterruptedException {
   
    //create twelve copies of one tradeable
    List<ITradeable> allTradeables = new LinkedList<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, 24));
       
    new RunServer2(2121, new LemonadeSetup()).runSimpleSim(allTradeables, new LemonadeRules(), 
        new LemonadeConfig(), 0., new LinkedList<ITradeable>());;
  }
}
