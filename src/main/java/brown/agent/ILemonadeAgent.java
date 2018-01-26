package brown.agent;

import brown.channels.agent.library.LemonadeChannel;

public interface ILemonadeAgent {

  /**
   * Provides agent response in lemonade game
   * @param simpleWrapper - lemonade channel
   */
  public abstract void onLemonade(LemonadeChannel channel);

}
