package brown.auction.prevstate; 

import brown.auction.prevstate.library.PrevStateInfo;
import brown.platform.managers.IValuationManager;

/**
 * PrevStateInfo is an object used to transmit information
 * between markets being run at different times in the simulation.
 * @author acoggins
 */
public interface IPrevStateInfo {

  /**
   * Combines this report with information from another report.
   * @param prevState
   */
  public void combine(PrevStateInfo prevState);

  /**
   * initialized prev state info. This depends on the 
   * simulation's valuation configuation.
   * @param gconfig
   */
  public void initialize(IValuationManager gconfig);
  
  /**
   * Gets the type of the prev state info.
   * @return
   */
  public PrevStateType getType();
}