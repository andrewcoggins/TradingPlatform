package brown.platform.managers.library;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.communication.messages.IValuationMessage;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.IValuationManager;
import brown.platform.tradeable.ITradeable;

public class ValuationManager implements IValuationManager {

    private List<IValuationDistribution> distributions;
    private Map<Integer, IValuation> agentValuations; 
    private boolean lock;
    
    // TODO: must be multiple. 
    
    public ValuationManager() {

        this.lock = false;
        this.agentValuations = new HashMap<Integer,  IValuation>();
        this.distributions = new LinkedList<IValuationDistribution>(); 
    }

    public void createValuation(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        Map<String, List<ITradeable>> tradeables) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            this.distributions.add((IValuationDistribution) distCons.newInstance(tradeables, generatorList)); 
        } else {
            PlatformLogging.log("Creation denied: valuation manager locked.");
        }
    }
    
    public void addAgentValuation(Integer agentID, List<String> tradeableNames, IValuation valuation) {
        this.agentValuations.put(agentID, valuation);
    }
    
    public IValuation getAgentValuation(Integer agentID) {
        return this.agentValuations.get(agentID);
    }
    
    // TODO: but... how to tell which tradeables correspond to each distribution? 
    // TODO: get distributions, but also need to know the tradeables that they correspond to? 
    public List<IValuationDistribution> getDistribution() {
      return this.distributions;
    }

    public void lock() {
        this.lock = true;
    }

    @Override
    public Map<Integer, IValuationMessage> constructValuationMessages() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public void reset() {
      this.agentValuations.clear();
    }   
    
}
