package brown.agent;

import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;
import brown.channels.library.AuctionChannel;

public interface ISpecValAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onQueryMarket(QueryChannel channel);
  
  public void onClockMarket(OpenOutcryChannel channel);
  
}
