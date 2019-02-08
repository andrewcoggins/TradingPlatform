package brown.communication.channel.library;

import brown.communication.bid.IBid;
import brown.user.agent.library.AbsAgent;

/**
 * agent channel used in call market auctions.
 * @author kerry
 *
 */
public class TwoSidedChannel extends AbsChannel{

  @Override
  public void dispatchMessage(AbsAgent agent) {

    }

  @Override
  public void bid(AbsAgent agent, IBid bid) {
    
  }
  
}
