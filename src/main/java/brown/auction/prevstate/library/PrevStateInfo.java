package brown.auction.prevstate.library;

import brown.auction.prevstate.IPrevStateInfo;
import brown.auction.prevstate.PrevStateType;
import brown.auction.value.config.IValuationConfig;

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
