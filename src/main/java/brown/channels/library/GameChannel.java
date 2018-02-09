package brown.channels.library;

import brown.agent.AbsAgent;
import brown.agent.AbsLemonadeAgent;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.logging.Logging;
import brown.messages.library.TradeMessage;

/**
 * Game channel is the channel through which server and agent communicate in non-auction games.
 * @author andrew
 */
public class GameChannel extends AbsChannel {

  /**
   * For Kryo
   * DO NOT USE
   */
  public GameChannel() { 
    super();
  }
  
  /**
   * Constructor
   * @param ID
   */
  public GameChannel(Integer ID) {
    super(ID);
  }

  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsLemonadeAgent) {
      AbsLemonadeAgent lemonadeAgent = (AbsLemonadeAgent) agent; 
      lemonadeAgent.onLemonade(this);   
    }
  }
  
  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    if (bid.getType() == BundleType.GAME) {
      agent.CLIENT.sendTCP(new TradeMessage(0, bid, this.ID, agent.ID));      
    } else {
      Logging.log("[Channel encountered invalid bid type]");
      return;      
    }
  }
  
}