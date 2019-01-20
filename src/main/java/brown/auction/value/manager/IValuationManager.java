package brown.auction.value.manager;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.ITradeable;

public interface IValuationManager {

    void createValuation(String tradeableName, Constructor<?> distCons, Map<Constructor<?>, List<Double>> generatorCons, 
        Set<ITradeable> tradeables) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    IValuationDistribution getDistribution(String tradeableName);

    void lock();
}
