package brown.agent;

import brown.channels.agent.library.SSSPChannel;

public interface ISSSPAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param channel - simple agent channel
   */
  public void onSSSP(SSSPChannel channel);
  
}
