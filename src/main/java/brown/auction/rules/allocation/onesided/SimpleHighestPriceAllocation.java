package brown.auction.rules.allocation.onesided;

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

/**
 * allocation rule that allocates the item(s) to the highest bidder.
 * 
 * @author andrewcoggins
 *
 */
public class SimpleHighestPriceAllocation extends AbsRule
    implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
    // TODO: do I check that bids are single here, or in activity rule?
    Map<Integer, List<ICart>> highest = new HashMap<Integer, List<ICart>>();
    Map<ICart, Double> cartHighest = new HashMap<ICart, Double>();
    for (ITradeMessage message : messages) {
      IBidBundle bundle = (IBidBundle) message.getBid();
      Map<ICart, Double> bids = bundle.getBids();
      for (ICart cartBid : bids.keySet()) {
        if (!cartHighest.keySet().contains(cartBid)) {

          List<ICart> existingBids = new LinkedList<ICart>();
          if (highest.containsKey(message.getAgentID())) {
            existingBids = highest.get(message.getAgentID());
          }
          existingBids.add(cartBid);
          cartHighest.put(cartBid, bids.get(cartBid));
          highest.put(message.getAgentID(), existingBids);
        } else {
          // if it's greater, or equal and luck is on your side. 
          if (bids.get(cartBid) > cartHighest.get(cartBid)
              || (bids.get(cartBid) == cartHighest.get(cartBid)
                  && Math.random() > 0.5)) {
            cartHighest.put(cartBid, bids.get(cartBid));
            List<ICart> existingBids = new LinkedList<ICart>();
            if (highest.containsKey(message.getAgentID())) {
              existingBids = highest.get(message.getAgentID());
            }
            existingBids.add(cartBid);
            highest.put(message.getAgentID(), existingBids);
            // remove from other agents' inventories.
            for (Integer agentID : highest.keySet()) {
              if (agentID == message.getAgentID()) {
                continue;
              } else {
                List<ICart> agentCarts = highest.get(agentID);
                if (agentCarts.contains(cartBid)) {
                  agentCarts.remove(cartBid);
                  highest.put(agentID, agentCarts);
                  break;
                }
              }
            }
          }
        }
      }
    }
    state.setAllocation(highest);
  };
}
