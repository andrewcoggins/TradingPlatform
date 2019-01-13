package brown.platform.input.config.library;

import java.util.List;

import brown.platform.input.config.IEndowmentConfig;
import brown.platform.input.config.IMarketConfig;
import brown.platform.input.config.ISimulationConfig;
import brown.platform.input.config.ITradeableConfig;

public class SimulationConfig implements ISimulationConfig {

  
    private Integer simulationRuns;
    private List<ITradeableConfig> tConfig; 
    private List<IEndowmentConfig> eConfig; 
    private List<List<IMarketConfig>> mConfig; 
    
    public SimulationConfig(Integer simulationRuns, List<ITradeableConfig> tConfig, List<IEndowmentConfig> eConfig,
                            List<List<IMarketConfig>> mConfig) {

        this.simulationRuns = simulationRuns;
        this.tConfig = tConfig; 
        this.eConfig = eConfig; 
        this.mConfig = mConfig; 
    }

    @Override
    public Integer getSimulationRuns() {
      
      return this.simulationRuns;
    }

    @Override
    public List<ITradeableConfig> getTConfig() {
      
      return this.tConfig;
    }

    @Override
    public List<IEndowmentConfig> getEConfig() {
      
      return this.eConfig;
    }

    @Override
    public List<List<IMarketConfig>> getMConfig() {
      
      return this.mConfig;
    }
}
