package brown.market.library;

import brown.value.config.GameValuationConfig;

public abstract class PrevStateInfo {
  
  // Combine this report with information from another report
  public abstract void combine(PrevStateInfo prevState);

  // Initialize the report
  public abstract void initialize(GameValuationConfig gconfig);
  
  public abstract PrevStateType getType();
}
