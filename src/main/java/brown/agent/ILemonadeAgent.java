package brown.agent;

import brown.channels.library.GameChannel;

public interface ILemonadeAgent {

  /**
   * Provides agent response in lemonade game
   * @param channel - lemonade channel
   */
  public void onLemonade(GameChannel channel);

}
