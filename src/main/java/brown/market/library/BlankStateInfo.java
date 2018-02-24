package brown.market.library;

import brown.value.config.GameValuationConfig;

public class BlankStateInfo extends PrevStateInfo{
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
  public void initialize(GameValuationConfig gconfig) {    
  }

  @Override
  public PrevStateType getType() {
    // TODO Auto-generated method stub
    return this.type;
  }
}
