package brown.platform.managers;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.ITradeable;

public interface IValuationManager {

    void createValuation(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generatorCons, 
        Map<String, List<ITradeable>> tradeables) throws InstantiationException, IllegalAccessException,
    IllegalArgumentException, InvocationTargetException;

    IValuationDistribution getDistribution(String tradeableName);

    void lock();
}
