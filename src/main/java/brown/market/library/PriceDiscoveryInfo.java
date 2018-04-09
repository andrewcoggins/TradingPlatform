package brown.market.library; 

import brown.bidbundle.IBidBundle;
import brown.value.config.IValuationConfig;

/**
 * prev state info indicating the reserve prices of the previous auction.
 * @author acoggins
 *
 */
public class PriceDiscoveryInfo extends PrevStateInfo {
  
  public IBidBundle reservePrices; 
  private PrevStateType type; 
  
  public PriceDiscoveryInfo(IBidBundle reservePrices) {
    this.reservePrices = reservePrices; 
    this.type = PrevStateType.DISCOVERY; 
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
    return this.type; 
  }
  
}