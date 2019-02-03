package brown.auction.prevstate.library; 

import java.util.Map;

import brown.auction.prevstate.PrevStateType;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.managers.IValuationManager;

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
  public void initialize(IValuationManager gconfig) {
    // noop
  }

  @Override
  public PrevStateType getType() {
    return PrevStateType.DISCOVERY;
  }
  
}