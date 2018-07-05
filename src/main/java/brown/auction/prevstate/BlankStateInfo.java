package brown.auction.prevstate;

import brown.auction.value.config.IValuationConfig;

/**
 * This is a PrevStateInfo for the cases where it is not needed.
 * @author acoggins
 *
 */
public class BlankStateInfo extends PrevStateInfo {
  PrevStateType type;
  
  public BlankStateInfo() {
    this.type = PrevStateType.BLANK;
  }
  
  @Override
  public String toString() {
    return "BlankStateInfo";
  }

  @Override
  public void combine(PrevStateInfo prevState) {    
  }

  @Override
  public void initialize(IValuationConfig gconfig) {    
  }

  @Override
  public PrevStateType getType() {
    return this.type;
  }
}
