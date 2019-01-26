package brown.user.main;

import java.util.List;

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

    @Override
    public String toString() {
      return "SimulationConfig [simulationRuns=" + simulationRuns + ", tConfig="
          + tConfig + ", eConfig=" + eConfig + ", mConfig=" + mConfig + "]";
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((eConfig == null) ? 0 : eConfig.hashCode());
      result = prime * result + ((mConfig == null) ? 0 : mConfig.hashCode());
      result = prime * result
          + ((simulationRuns == null) ? 0 : simulationRuns.hashCode());
      result = prime * result + ((tConfig == null) ? 0 : tConfig.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      SimulationConfig other = (SimulationConfig) obj;
      if (eConfig == null) {
        if (other.eConfig != null)
          return false;
      } else if (!eConfig.equals(other.eConfig))
        return false;
      if (mConfig == null) {
        if (other.mConfig != null)
          return false;
      } else if (!mConfig.equals(other.mConfig))
        return false;
      if (simulationRuns == null) {
        if (other.simulationRuns != null)
          return false;
      } else if (!simulationRuns.equals(other.simulationRuns))
        return false;
      if (tConfig == null) {
        if (other.tConfig != null)
          return false;
      } else if (!tConfig.equals(other.tConfig))
        return false;
      return true;
    }
    
    
}
