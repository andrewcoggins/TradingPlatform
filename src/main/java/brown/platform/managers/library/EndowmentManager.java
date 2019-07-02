package brown.platform.managers.library;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.endowment.IEndowment;
import brown.auction.endowment.distribution.IEndowmentDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.logging.library.PlatformLogging;
import brown.platform.item.ICart;
import brown.platform.managers.IEndowmentManager;

public class EndowmentManager implements IEndowmentManager {

    private List<IEndowmentDistribution> distributions;
    private Map<Integer, IEndowment> agentEndowments; 
    private boolean lock;
    
    
    public EndowmentManager() {

        this.lock = false;
        this.agentEndowments = new HashMap<Integer,  IEndowment>();
        this.distributions = new LinkedList<IEndowmentDistribution>(); 
    }

    public void createEndowment(Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators,
        ICart items) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.lock) {
          List<IValuationGenerator> generatorList = new LinkedList<IValuationGenerator>(); 
            for (Constructor<?> generator : generators.keySet()) {
              IValuationGenerator newGen = (IValuationGenerator) generator.newInstance(generators.get(generator)); 
              generatorList.add(newGen); 
            }
            this.distributions.add((IEndowmentDistribution) distCons.newInstance(items, generatorList)); 
        } else {
            PlatformLogging.log("Creation denied: Endowment manager locked.");
        }
    }
    
    public void addAgentEndowment(Integer agentID, IEndowment Endowment) {
        this.agentEndowments.put(agentID, Endowment);
    }
    
    public IEndowment getAgentEndowment(Integer agentID) {
        return this.agentEndowments.get(agentID);
    }
    
    public List<IEndowmentDistribution> getDistribution() {
      return this.distributions;
    }

    public void lock() {
        this.lock = true;
    }

    @Override
    public void reset() {
      this.agentEndowments.clear();
    }   
    
}

