package brown.user.agent;

import brown.mechanism.channel.library.TwoSidedChannel;

/**
 * Interface for agent that bids in call markets.
 * @author acoggins
 *
 */
public interface ICallMarketAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onCallMarket(TwoSidedChannel channel);
  
}
