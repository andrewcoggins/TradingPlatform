package brown.agent;

import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;

/**
 * Interface for an agent that bids in a spectrum auction
 * with an ascending round followed by a sealed bid round.
 * @author acoggins
 *
 */
public interface ISpecValAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onQueryMarket(QueryChannel channel);
  
  /**
   * Provides agent response to clock auction.
   * @param channel
   */
  public void onClockMarket(OpenOutcryChannel channel);
  
}
