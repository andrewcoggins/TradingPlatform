package brown.mechanism.tradeable;

import java.util.List;

/**
 * ITradeableManager: manages the creation of tradeables for an auction.
 */
public interface ITradeableManager {

    /**
     * createSimpleTradeables: creates simple tradeables.
     * @param numTradeables number of tradeables to be created.
     */
    void createTradeables(int numTradeables);
    

    /**
     * getter for itradeabes
     * @return tradeables created in tradeableManager
     */
    List<ITradeable> getTradeables();

    void lock();
}
