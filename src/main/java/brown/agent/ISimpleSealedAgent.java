package brown.agent;

import brown.channels.library.AuctionChannel;

public interface ISimpleSealedAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onSSSP(AuctionChannel channel);
  
}
