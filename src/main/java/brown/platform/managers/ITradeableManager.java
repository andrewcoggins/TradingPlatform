package brown.platform.managers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import brown.platform.tradeable.ITradeable;

/**
 * ITradeableManager: manages the creation of tradeables for an auction.
 */
public interface ITradeableManager {

    /**
     * createTradeables: creates simple tradeables.
     * @param numTradeables number of tradeables to be created.
     */
    void createTradeables(String name, Constructor<?> tType, int numTradeables) throws InstantiationException,
    IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
     * getter for itradeabes
     * @return tradeables created in tradeableManager
     */
    List<ITradeable> getTradeables(String name);

    
    void lock();
}
