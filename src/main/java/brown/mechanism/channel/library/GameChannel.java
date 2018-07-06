package brown.mechanism.channel.library;

import java.util.Map;

import brown.logging.library.Logging;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.bidbundle.library.BundleType;
import brown.mechanism.channel.IAgentChannel;
import brown.platform.messages.library.TradeMessage;
import brown.user.agent.library.AbsAgent;
import brown.user.agent.library.AbsLemonadeAgent;

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

  @Override
  public IAgentChannel sanitize(Integer agent,Map<Integer, Integer> privateToPublic) {
    return this;
  }
  
}