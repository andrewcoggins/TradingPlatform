package brown.agent;

import brown.channels.agent.library.LemonadeChannel;

public interface ILemonadeAgent {

  /**
   * Provides agent response in lemonade game
   * @param channel - lemonade channel
   */
  public void onLemonade(LemonadeChannel channel);

}
