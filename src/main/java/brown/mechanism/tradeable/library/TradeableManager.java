package brown.mechanism.tradeable.library;

import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;

import java.util.List;
import java.util.LinkedList;

/**
 * A TradeableManager is an entity that creates tradeables for an auction.
 *
 */

public class TradeableManager implements ITradeableManager {

    private List<ITradeable> tradeables;
    private boolean lock;

    /**
     * initializes the tradeabeles variable to an initial empty value
     */
    public TradeableManager() {
        this.tradeables = new LinkedList<>();
        this.lock = false;
    }

    public void createTradeables(int numTradeables) {
        try {
            if (!lock) {
                List<ITradeable> tentative = new LinkedList<>();
                for (int i = 0; i < numTradeables; i++) {
                    tentative.add(new SimpleTradeable(i));
                }
                if (this.tradeables.isEmpty()) {
                     this.tradeables = tentative;
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

    public List<ITradeable> getTradeables() {
        return this.tradeables;
    }

    public void lock() {
        this.lock = true;
    }

}
