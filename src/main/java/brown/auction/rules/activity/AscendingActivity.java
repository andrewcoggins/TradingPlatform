package brown.auction.rules.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class AscendingActivity extends AbsActivity implements IActivityRule {

  @Override
  public void isAcceptable(IMarketState state, ITradeMessage aBid,
      List<ITradeMessage> currentBids, ICart items) {

    // check that the submitted bid is greater than or equal to the reserve for
    // that agent (they should all be the same here).

    Map<String, Double> reserves = state.getReserves();

    Integer bidAgentID = aBid.getAgentID();

    if (!isWellFormed(aBid, items)) {
      state.setAcceptable(false);
    } else {
      if (reserves.keySet().contains(bidAgentID)) {
        IBidBundle bundle = (IBidBundle) aBid.getBid();
        Map<ICart, Double> carts = bundle.getBids();
        state.setAcceptable(true);
        if (carts.keySet().size() > 1) {
          state.setAcceptable(false);
        } else {
          for (ICart cart : carts.keySet()) {
            // all carts have only one item
            if (cart.getItems().size() != 1) {
              state.setAcceptable(false);
              break;
            }
            // all items are size 1
            if (cart.getItems().get(0).getItemCount() != 1) {
              state.setAcceptable(false);
              break;
            }
            // main condition: if the bid is too low for the reserve, reject.
            String itemName = cart.getItems().get(0).getName();
            if (carts.get(cart) < reserves.get(itemName)) {
              state.setAcceptable(false);
            }
          }
        }
      } else {
        state.setAcceptable(false);
      }
    }
  }

  @Override
  public void setReserves(IMarketState state, ICart items) {
    // this is hard-coded here...
    double baseReserve = 10.0;
    int timeStep = state.getTicks();

    Map<String, Double> reserves = new HashMap<String, Double>();

    for (IItem item : items.getItems()) {
      reserves.put(item.getName(), baseReserve * timeStep);
    }

  }

}
