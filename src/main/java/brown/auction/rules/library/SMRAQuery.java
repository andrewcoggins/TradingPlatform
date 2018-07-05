package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IQueryRule;
import brown.mechanism.channel.OpenOutcryChannel;
import brown.platform.messages.TradeRequestMessage;

/**
 * Query rule for SMRA auction. Untested.
 * @author acoggins
 *
 */
public class SMRAQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    // make a channel that gives everyone the round's prices.
    Map<Integer,Integer> idToGroup = new HashMap<Integer,Integer>();
    for (List<Integer> agents : state.getGroups()) {
      for (Integer a : agents) {
        idToGroup.put(a, agents.size());
      }
    }   
    state.setTRequest(
        new TradeRequestMessage(0,
            new OpenOutcryChannel(
                state.getID(),
                state.getReserve()),
                idToGroup)); 
  }

  @Override
  public void reset() {
    //noop
  }
  
}