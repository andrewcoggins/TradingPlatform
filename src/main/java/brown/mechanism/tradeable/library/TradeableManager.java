package brown.mechanism.tradeable.library;

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

//TODO: fix this.
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

    public void createSimpleTradeables(int numTradeables) {
        try {
            if (!lock) {
                List<ITradeable> tentative = new LinkedList<>();
                for (int i = 0; i < numTradeables; i++) {
                    tentative.add(new SimpleTradeable(i));
                }
                if (this.tradeables.isEmpty()) {
                    this.tradeables = tentative;
                } else {
                    System.err.println("ERROR: tradeables already assigned.");
                }
            } else {
                PlatformLogging.log("Creation denied: tradeable manager locked.");
            }
        } catch (NotStrictlyPositiveException e) {
            System.err.println("NotStrictlyPositiveException: " + e);
        }
    }

    public void createMultiTradeables(List<MultiTradeable> multiTradeables) {
        if (this.tradeables.isEmpty()) {
            List<ITradeable> converted = new LinkedList<>(multiTradeables);
            this.tradeables = converted;
        } else {
            System.err.println("ERROR: tradeables already assigned.");
        }
    }

    public void createComplexTradeables(List<ComplexTradeable> complexTradeables) {
        if (this.tradeables.isEmpty()) {
            List<ITradeable> converted = new LinkedList<>(complexTradeables);
            this.tradeables = converted;
        } else {
            System.err.println("ERROR: tradeables already assigned.");
        }
    }

    public List<ITradeable> getTradeables() {
        return this.tradeables;
    }

    public void lock() {
        this.lock = true;
    }
}
