package brown.auction.value.manager.library;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.manager.IValuationManager;
import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;

public class ValuationManager implements IValuationManager {

    private Map<String, IValuationDistribution> distributions;
    private boolean lock;

    public ValuationManager() {
        this.distributions = new HashMap<String, IValuationDistribution>();
        this.lock = false;
    }

    public void createValuation(String tradeableName, Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        Set<ITradeable> tradeables) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            IValuationDistribution distribution = (IValuationDistribution) distCons.newInstance(generatorList, tradeables); 
            this.distributions.put(tradeableName, distribution);
        } else {
            PlatformLogging.log("Creation denied: valuation manager locked.");
        }
    }

    public IValuationDistribution getDistribution(String tradeableName) {
        return this.distributions.get(tradeableName);
    }

    public void lock() {
        this.lock = true;
    }
}
