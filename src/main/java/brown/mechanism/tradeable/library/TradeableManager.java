package brown.mechanism.tradeable.library;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;

import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;

/**
 * A TradeableManager is an entity that creates tradeables for an auction.
 *
 */

public class TradeableManager implements ITradeableManager {

    private Map<String, List<ITradeable>> tradeables;
    private boolean lock;

    /**
     * initializes the tradeabeles variable to an initial empty value
     */
    public TradeableManager() {
        this.tradeables = new HashMap<String, List<ITradeable>>();
        this.lock = false;
    }
    
    @Override
    public void lock() {
        this.lock = true;
    }

    @Override
    public void createTradeables(String name, Constructor<?> tTypeCons, int numTradeables) throws InstantiationException,
    IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      try {
      if (!lock) {
          List<ITradeable> tentative = new LinkedList<>();
          for (int i = 0; i < numTradeables; i++) {
              tentative.add((ITradeable) tTypeCons.newInstance(i));
          }
          if (this.tradeables.isEmpty()) {
               this.tradeables.put(name, tentative); 
          } else {
              ErrorLogging.log("ERROR: tradeables already assigned.");
          }
      } else {
          PlatformLogging.log("Creation denied: tradeable manager locked.");
      }
  } catch (NotStrictlyPositiveException e) {
      ErrorLogging.log("NotStrictlyPositiveException: " + e);
  }
    }

    @Override
    public List<ITradeable> getTradeables(String name) {
      return this.tradeables.get(name); 
    }

}
