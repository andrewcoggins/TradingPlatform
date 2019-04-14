package brown.platform.managers.library;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;

import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ITradeableManager;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.Tradeable;

/**
 * A TradeableManager is an entity that creates tradeables for an auction.
 *
 */

public class TradeableManager implements ITradeableManager {

    private Map<String, List<ITradeable>> tradeables;
    private boolean lock;
    private int tradeableCount; 

    /**
     * initializes the tradeabeles variable to an initial empty value
     */
    public TradeableManager() {
        this.tradeables = new HashMap<String, List<ITradeable>>();
        this.lock = false;
        this.tradeableCount = 0; 
    }
    
    @Override
    public void lock() {
        this.lock = true;
    }

    @Override
    public void createTradeables(String name, int numTradeables) {
      try {
      if (!lock) {
          List<ITradeable> tentative = new LinkedList<>();
          for (int i = 0; i < numTradeables; i++) {
              tentative.add(new Tradeable(i + this.tradeableCount, name));
          }
          this.tradeableCount += numTradeables; 
          this.tradeables.put(name, tentative); 
      } else {
          PlatformLogging.log("Creation denied: tradeable manager locked.");
      }
  } catch (NotStrictlyPositiveException e) {
      ErrorLogging.log("NotStrictlyPositiveException: " + e);
  }
    }

    @Override
    public List<ITradeable> getTradeables(String name) {
      if (tradeables.keySet().contains(name)) {
        return this.tradeables.get(name); 
      } else {
        ErrorLogging.log("ERROR: TradeableManager: no such tradeable exists.");
        return new LinkedList<ITradeable>(); 
      }
    }

}
