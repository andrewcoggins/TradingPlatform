package brown.user.main.library;

import java.util.List;

import brown.user.main.IEndowmentConfig;
import brown.user.main.IItemConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.IValuationConfig;

public class SimulationConfig implements ISimulationConfig {

  private Integer simulationRuns;
  private Integer groupSize; 
  private List<IItemConfig> tConfig;
  private List<IValuationConfig> vConfig;
  private List<IEndowmentConfig> eConfig;
  private List<List<IMarketConfig>> mConfig;

  public SimulationConfig(Integer simulationRuns, Integer groupSize,
      List<IItemConfig> tConfig, List<IValuationConfig> vConfig,
      List<IEndowmentConfig> eConfig, List<List<IMarketConfig>> mConfig) {

    this.simulationRuns = simulationRuns;
    this.tConfig = tConfig;
    this.eConfig = eConfig;
    this.mConfig = mConfig;
    this.vConfig = vConfig;
    this.groupSize = groupSize; 
  }

  @Override
  public Integer getSimulationRuns() {

    return this.simulationRuns;
  }

  @Override
  public List<IItemConfig> getTConfig() {

    return this.tConfig;
  }

  @Override
  public List<IValuationConfig> getVConfig() {

    return this.vConfig;
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
  public Integer getGroupSize() {
    return this.groupSize;
  }

  @Override
  public String toString() {
    return "SimulationConfig [simulationRuns=" + simulationRuns + ", groupSize="
        + groupSize + ", tConfig=" + tConfig + ", vConfig=" + vConfig
        + ", eConfig=" + eConfig + ", mConfig=" + mConfig + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((eConfig == null) ? 0 : eConfig.hashCode());
    result = prime * result + ((groupSize == null) ? 0 : groupSize.hashCode());
    result = prime * result + ((mConfig == null) ? 0 : mConfig.hashCode());
    result = prime * result
        + ((simulationRuns == null) ? 0 : simulationRuns.hashCode());
    result = prime * result + ((tConfig == null) ? 0 : tConfig.hashCode());
    result = prime * result + ((vConfig == null) ? 0 : vConfig.hashCode());
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
    if (groupSize == null) {
      if (other.groupSize != null)
        return false;
    } else if (!groupSize.equals(other.groupSize))
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
    if (vConfig == null) {
      if (other.vConfig != null)
        return false;
    } else if (!vConfig.equals(other.vConfig))
      return false;
    return true;
  }

}
