package brown.channels.agent.library;

import brown.agent.AbsAgent;
import brown.agent.AbsLemonadeAgent;
import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.IBidBundle;
import brown.messages.library.TradeMessage;
import brown.setup.Logging;

/**
 * The channel through which an agent communicates to the server in the lemonade game.
 * @author andrew
 */
public class LemonadeChannel extends AbsChannel {

  public LemonadeChannel() { 
    super();
  }
  
  public LemonadeChannel(Integer ID) {
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