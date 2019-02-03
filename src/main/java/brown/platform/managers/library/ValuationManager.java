package brown.platform.managers.library;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IValuation;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.managers.IValuationManager;

public class ValuationManager implements IValuationManager {

    private Map<List<String>, IValuationDistribution> distributions;
    private Map<Integer, Map<List<String>, IValuation>> agentValuations; 
    private boolean lock;

    public ValuationManager() {
        this.distributions = new HashMap<List<String>, IValuationDistribution>();
        this.lock = false;
    }

    public void createValuation(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        Map<String, List<ITradeable>> tradeables) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            IValuationDistribution distribution = (IValuationDistribution) distCons.newInstance(generatorList, tradeables); 
            this.distributions.put(new LinkedList<String>(tradeables.keySet()), distribution);
        } else {
            PlatformLogging.log("Creation denied: valuation manager locked.");
        }
    }
    
    public void addAgentValuation(Integer agentID, List<String> tradeableNames, IValuation valuation) {
      if (!this.agentValuations.keySet().contains(agentID)) {
        Map<List<String>, IValuation> initialValuation = new HashMap<List<String>, IValuation>(); 
        initialValuation.put(tradeableNames, valuation); 
      } else {
        Map<List<String>, IValuation> existingValuation = this.agentValuations.get(agentID); 
        existingValuation.put(tradeableNames, valuation); 
        this.agentValuations.put(agentID, existingValuation); 
      }
    }
    
    public Map<List<String>, IValuation> getAgentValuation(Integer agentID) {
      try  {
        Map<List<String>, IValuation> agentValuation = this.agentValuations.get(agentID);
        Set<List<String>> keys = agentValuation.keySet(); 
        return agentValuation; 
      } catch(NullPointerException n) {
        ErrorLogging.log("ERROR: Valuation Manager: no such agent exists: " + agentID.toString());
        throw n; 
      } 
    }
    
    public IValuationDistribution getDistribution(List<String> tradeableNames) {
      return this.distributions.get(tradeableNames);
    }

    public void lock() {
        this.lock = true;
    }
    
}
