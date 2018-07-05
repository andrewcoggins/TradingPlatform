package brown.user.agent;

import brown.mechanism.channel.OpenOutcryChannel;

public interface IOpenOutcryAgent {

  /**
   * Provides agent response to open outcry auction
   * @param channel - simple agent channel
   */
  public abstract void onOpenOutcry(OpenOutcryChannel channel);

}
