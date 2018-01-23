package brown.messages.library;

import brown.agent.AbsAgent;

public abstract class MarketUpdateMessage extends AbsMessage{

  public MarketUpdateMessage(Integer ID) {
    super(ID);
  }

  public void dispatch(AbsAgent agent) {
    agent.onMarketUpdate(this);
  }
}
