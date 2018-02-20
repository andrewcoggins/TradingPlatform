package brown.channels.library;

import brown.agent.AbsAgent;
import brown.agent.AbsCallMarketAgent;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.logging.Logging;
import brown.messages.library.TradeMessage;

public class CallMarketChannel extends AbsChannel{
  /**
   * For Kryo
   * DO NOT USE
   */
  public CallMarketChannel() {
    super();
  }

  /**
   * Constructor
   * @param ID
   */
  public CallMarketChannel(Integer ID) {
    super(ID);
  }
  
  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsCallMarketAgent) {
      AbsCallMarketAgent absCallAgent = (AbsCallMarketAgent) agent; 
      absCallAgent.onCallMarket(this);
      }  
    }

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    if (bid.getType() == BundleType.TWOSIDED){
      agent.CLIENT.sendTCP(new TradeMessage(0,bid,this.ID,agent.ID));
    } else {
      Logging.log("[Channel encountered invalid bid type]");
      return;      
    }   
  }
}
