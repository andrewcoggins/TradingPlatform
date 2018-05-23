package brown.channels.library;

import java.util.Map;

import brown.agent.AbsAgent;
import brown.agent.AbsSpecValAgent;
import brown.bidbundle.IBidBundle;
import brown.channels.IAgentChannel;
import brown.messages.library.TradeMessage;

/**
 * channel for query auctions.
 * @author kerry
 *
 */
public class QueryChannel extends AbsChannel {

  /**
   * For Kryo
   * DO NOT USE
   */
  public QueryChannel() {
    super();
  }

  /**
   * Constructor
   * @param ID
   */
  public QueryChannel(Integer ID) {
    super(ID);
  }

  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsSpecValAgent) {
      AbsSpecValAgent absCallAgent = (AbsSpecValAgent) agent; 
      absCallAgent.onQueryMarket(this);
      }  
  }

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    agent.CLIENT.sendTCP(new TradeMessage(0,bid,this.ID,agent.ID));
  }

  @Override
  public IAgentChannel sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    return this;
  }

}
