package brown.auction.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IQueryRule;
import brown.mechanism.channel.library.OpenOutcryChannel;
import brown.platform.messages.library.TradeRequestMessage;

public class ClockQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()){
      for (Integer a : agents) {
        idToGroup.put(a, agents.size());
      }
    }           
    state.setTRequest(new TradeRequestMessage(0,new OpenOutcryChannel(state.getID(),state.getReserve()),idToGroup)); 
  }

}
