package brown.auction.prevstate; 

import java.util.Map;

import brown.auction.value.config.IValuationConfig;
import brown.mechanism.tradeable.ITradeable;

/**
 * prev state info indicating the reserve prices of the previous auction.
 * @author acoggins
 *
 */
public class PriceDiscoveryInfo extends PrevStateInfo {
  
  public Map<ITradeable, Double> reservePrices; 
  
  public PriceDiscoveryInfo(Map<ITradeable, Double> reservePrices) {
    this.reservePrices = reservePrices;  
  }
  
  @Override
  public void combine(PrevStateInfo prevState) {
    if (prevState.getType() == PrevStateType.DISCOVERY) {
      this.reservePrices = ((PriceDiscoveryInfo) prevState).reservePrices; 
    }
  }

  
  @Override
  public void initialize(IValuationConfig gconfig) {
    // noop
  }

  @Override
  public PrevStateType getType() {
    return PrevStateType.DISCOVERY;
  }
  
}