package brown.agent;

import brown.channels.library.CallMarketChannel;

public interface ICallMarketAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onCallMarket(CallMarketChannel channel);
  
}
