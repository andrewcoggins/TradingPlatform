package brown.agent;

import brown.channels.agent.library.SSSPChannel;

public interface ISSSPAgent {

  /**
   * Provides agent response to sealed-bid auction
   * @param simpleWrapper - simple agent channel
   */
  public abstract void onSSSP(SSSPChannel channel);
}
