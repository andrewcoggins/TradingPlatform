package brown.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.channels.library.CallMarketChannel;
import brown.channels.library.QueryChannel;
import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class QueryRoundQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()){
      for (Integer a : agents) {
        idToGroup.put(a, agents.size());
      }
    }       
    TradeRequestMessage tr = new TradeRequestMessage(0, new QueryChannel(state.getID()),idToGroup);
    Logging.log("TICKS: " + state.getTicks());
    state.setTRequest(tr);
  }

  @Override
  public void reset() {
  }

}
