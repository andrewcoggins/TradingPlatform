package brown.auction.rules.allocation.onesided;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;

public class LastBidderSingleItem extends AbsRule implements IAllocationRule {

  // sets the allocation to the 'last bid standing' in an ascending auction.
  // if there are multiple last bids standing, chooses one at random.
  
  // SINGLE GOOD ONLY
  @Override
  public void setAllocation(IMarketState state, List<ITradeMessage> messages) {

    Map<Integer, List<ICart>> allocation = new HashMap<Integer, List<ICart>>();

    // get the most recent history.
    List<List<ITradeMessage>> tradeHistory = state.getTradeHistory();
    if (!tradeHistory.isEmpty()) {
      List<ITradeMessage> mostRecent =
          tradeHistory.get(tradeHistory.size() - 1);
      List<ICart> winningCarts = new LinkedList<ICart>();
      // shuffle to randomize. 
      // if this is too inefficient, could also do a random number the size of the list. 
      Collections.shuffle(mostRecent);
      ITradeMessage winningTrade = mostRecent.get(0);
      Integer agentID = winningTrade.getAgentID();
      IBidBundle bundle = (IBidBundle) winningTrade.getBid();
      Map<ICart, Double> bids = bundle.getBids();
      for (ICart cartBid : bids.keySet()) {
        winningCarts.add(cartBid);
      }
      allocation.put(agentID, winningCarts);
      state.setAllocation(allocation);
    }
  }

}
