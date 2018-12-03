package brown.user.agent;

import brown.mechanism.channel.library.OneSidedChannel;

/**
 * Specval agent for single ascending round without a sealed bid round.
 * @author kerry
 *
 */
public interface ISpecValV2Agent {
  
  /**
   * response to ascending auction query. 
   * @param channel
   */
  public void onClockMarket(OneSidedChannel channel);

}
