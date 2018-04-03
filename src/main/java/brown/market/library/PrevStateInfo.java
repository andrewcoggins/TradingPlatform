package brown.market.library;

import brown.value.config.IValuationConfig;

public abstract class PrevStateInfo {
  
  // Combine this report with information from another report
  public abstract void combine(PrevStateInfo prevState);

  // Initialize the report
  public abstract void initialize(IValuationConfig gconfig);
  
  public abstract PrevStateType getType();
}
