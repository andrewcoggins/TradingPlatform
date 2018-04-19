package brown.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.channels.library.OpenOutcryChannel;
import brown.channels.library.QueryChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class MixedQueryClockQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()){
      for (Integer a : agents) {
        idToGroup.put(a, agents.size());
      }
    }           
    if (state.getTicks() % 2 == 1){
      // do the query round
      state.setTRequest(new TradeRequestMessage(0, new QueryChannel(state.getID()),idToGroup));
    } else {
     // do the clock round 
      state.setTRequest(new TradeRequestMessage(0,new OpenOutcryChannel(state.getID(),state.getReserve()),idToGroup)); 
    }
  }

  @Override
  public void reset() {
  }

}
