package brown.platform.managers.library;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.messages.IValuationMessage;
import brown.logging.library.PlatformLogging;
import brown.platform.item.ICart;
import brown.platform.managers.IValuationManager;

public class ValuationManager implements IValuationManager {

    private List<IValuationDistribution> distributions;
    private Map<Integer, IGeneralValuation> agentValuations; 
    private boolean lock;
    
    
    public ValuationManager() {

        this.lock = false;
        this.agentValuations = new HashMap<Integer,  IGeneralValuation>();
        this.distributions = new LinkedList<IValuationDistribution>(); 
    }

    public void createValuation(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        ICart items) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            this.distributions.add((IValuationDistribution) distCons.newInstance(items, generatorList)); 
        } else {
            PlatformLogging.log("Creation denied: valuation manager locked.");
        }
    }
    
    public void addAgentValuation(Integer agentID, IGeneralValuation valuation) {
        this.agentValuations.put(agentID, valuation);
    }
    
    public IGeneralValuation getAgentValuation(Integer agentID) {
        return this.agentValuations.get(agentID);
    }
    
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
