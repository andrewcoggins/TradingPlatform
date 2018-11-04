package brown.mechanism.tradeable;

import brown.mechanism.tradeable.library.ComplexTradeable;
import brown.mechanism.tradeable.library.MultiTradeable;
import java.util.List;

/**
 * ITradeableManager: manages the creation of tradeables for an auction.
 */
public interface ITradeableManager {

    /**
     * createSimpleTradeables: creates simple tradeables.
     * @param numTradeables number of tradeables to be created.
     */
    void createSimpleTradeables(int numTradeables);

    /**
     * creates multiTradeables.
     * @param multiTradeables a list of multiTradeables to be created.
     */
    void createMultiTradeables(List<MultiTradeable> multiTradeables);

    /**
     * creates complexTradeables.
     * @param complexTradeables a list of complexTradeables to be created.
     */
    void createComplexTradeables(List<ComplexTradeable> complexTradeables);

    /**
     * getter for itradeabes
     * @return tradeables created in tradeableManager
     */
    List<ITradeable> getTradeables();

    void lock();
}
