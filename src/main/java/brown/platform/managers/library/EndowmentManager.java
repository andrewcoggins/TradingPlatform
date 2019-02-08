package brown.platform.managers.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.InitialEndowment;
import brown.platform.managers.IEndowmentManager;
import brown.platform.tradeable.ITradeable;

import java.util.concurrent.ThreadLocalRandom;


public class EndowmentManager implements IEndowmentManager {

    private boolean lock; 
    
    private Map<String, Integer> endowmentAlias; 
    private Map<String, Map<String, List<ITradeable>>> endowments; 
    private Map<String, Double> endowmentMoneyAlias; 
    
    public EndowmentManager() {
        this.lock = false; 
    }

    @Override
    public void createEndowment(String endowmentName,
        Map<String, Integer> endowmentMapping,
        Map<String, List<String>> includeMapping, Integer frequency,
        Map<String, List<ITradeable>> allTradeables, Double money) {
      if (!this.lock) {
        endowmentAlias.put(endowmentName, frequency);
        Map<String, List<ITradeable>> endowment = new HashMap<String, List<ITradeable>>(); 
        for (String tName : endowmentMapping.keySet()) {
          List<ITradeable> allT = allTradeables.get(tName);
          Integer numT = endowmentMapping.get(tName); 
          List<ITradeable> newT = new LinkedList<ITradeable>(); 
          for (int i = 0; i < numT; i++) {
            newT.add(allT.get(i)); 
          }
          endowment.put(tName, newT); 
        }
        endowments.put(endowmentName, endowment); 
        endowmentMoneyAlias.put(endowmentName, money); 
      } else {
        PlatformLogging.log("Creation denied: endowment manager locked.");
      }
    }
    
    @Override
    public IInitialEndowment getEndowment() {
      // need to get based on the frequency. 
      List<String> freqStrings = new LinkedList<String>(); 
      for (String s : endowmentAlias.keySet()) {
        for (int i = 0; i < endowmentAlias.get(s); i++) {
          freqStrings.add(s); 
        }
      }
      int freq = 0; 
      for (String s : endowmentAlias.keySet()) {
        freq += this.endowmentAlias.get(s); 
      }
      int randomNum = ThreadLocalRandom.current().nextInt(0, freq);
      String endowmentString = freqStrings.get(randomNum); 
      Map<String, List<ITradeable>> endowment = this.endowments.get(endowmentString); 
      Double endowmentMoney = this.endowmentMoneyAlias.get(endowmentString); 
      return new InitialEndowment(endowmentMoney, endowment); 
    }
    
    @Override
    public void lock() {
      this.lock = true; 
    }
    
}
