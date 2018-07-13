package brown.auction.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IQueryRule;
import brown.mechanism.channel.library.CallMarketChannel;
import brown.platform.messages.library.TradeRequestMessage;

/**
 * Query rule for a call market Simply constructs a traderequest
 * with a call market channel with the most updated version of the orderbook.
 * @author kerry
 *
 */
public class CallMarketQuery implements IQueryRule {
  @Override
  public void makeChannel(IMarketState state) {           
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()){
      for (Integer a : agents) {
        idToGroup.put(a, agents.size());
      }
    }       
    TradeRequestMessage tr = new TradeRequestMessage(0, new CallMarketChannel(state.getID(),state.getOrderBook(),false),idToGroup);
    state.setTRequest(tr);
  }

}
