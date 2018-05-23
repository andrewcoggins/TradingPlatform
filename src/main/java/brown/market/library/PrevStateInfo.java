package brown.market.library;

import brown.market.IPrevStateInfo;
import brown.value.config.IValuationConfig;

/**
 * Abstract class for prev state info (see IPrevStateInfo).
 * @author acoggins
 *
 */
public abstract class PrevStateInfo implements IPrevStateInfo {
  
  public abstract void combine(PrevStateInfo prevState);

  public abstract void initialize(IValuationConfig gconfig);
  
  public abstract PrevStateType getType();
}
