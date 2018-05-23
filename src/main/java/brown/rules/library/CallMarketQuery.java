package brown.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.channels.library.CallMarketChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

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

  @Override
  public void reset() {    
  } 
}
