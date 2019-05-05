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

    private IValuationDistribution distribution;
    private Map<Integer, IValuation> agentValuations; 
    private boolean lock;
    
    // TODO: can be multiple. 
    
    public ValuationManager() {

        this.lock = false;
        this.agentValuations = new HashMap<Integer,  IValuation>();       
    }

    public void createValuation(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        Map<String, List<ITradeable>> tradeables) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            this.distribution = (IValuationDistribution) distCons.newInstance(tradeables, generatorList); 
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
    
    public IValuationDistribution getDistribution() {
      return this.distribution;
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
