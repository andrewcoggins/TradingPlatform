package brown.auction.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IQueryRule;
import brown.mechanism.channel.GameChannel;
import brown.platform.messages.TradeRequestMessage;

public class LemonadeQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()){
      for (Integer a : agents){
        idToGroup.put(a, agents.size());
      }
    }    
    state.setTRequest(new TradeRequestMessage(0,new GameChannel(state.getID()), idToGroup));      
  }

  @Override
  public void reset() {
  }  
}
