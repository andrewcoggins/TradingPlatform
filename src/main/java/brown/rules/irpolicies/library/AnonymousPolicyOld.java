package brown.rules.irpolicies.library;

import java.util.LinkedList;
import java.util.List;

import brown.accounting.bidbundle.IBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.rules.irpolicies.IInformationRevelationPolicy;

public class AnonymousPolicyOld implements IInformationRevelationPolicy {

//	@Override
//	public MarketInternalState handleInfo(Integer ID, MarketInternalState state) {
//		BidBundle cleanedAlloc = state.getAllocation();
//		cleanedAlloc = cleanedAlloc.wipeAgent(ID);
//		MarketInternalState newState = new SimpleInternalState(state.getID(), state.getTradeables());
//		for (Bid b : state.getBids()) {
//			newState.addBid(b);
//		}
//		List<Order> newPayments = new LinkedList<Order>();
//		for (Order o : state.getPayments()) {
//			if (o.TO.equals(ID)) {
//				newPayments.add(o);
//			} else {
//				newPayments.add(o.updateToAgent(null));
//			}
//		}
//		newState.setPayments(newPayments);
//		newState.setAllocation(cleanedAlloc);
//		return newState;
//	}

  @Override
  public void handleInfo() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

}
