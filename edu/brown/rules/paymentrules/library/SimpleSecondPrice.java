package brown.rules.paymentrules.library;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.rules.paymentrules.PaymentRule;
import brown.setup.Logging;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;


public class SimpleSecondPrice implements PaymentRule {

  @Override
  public void getPayments(MarketInternalState state) {
    // TODO Auto-generated method stub
    BidBundle highest = state.getAllocation();
    if (!highest.getType().equals(BundleType.Simple)) {
      Logging.log("ERROR: bundle type not simple");
    }
    SimpleBidBundle bundle = (SimpleBidBundle) highest;      
    Map<Tradeable, MarketState> nextHighest = new HashMap<Tradeable, MarketState>();
    //get the bids again.
    for(Bid b : state.getBids()) {
      if (!highest.getType().equals(BundleType.Simple)) {
        Logging.log("ERROR: bundle type not simple");
      }
      SimpleBidBundle otherBundle = (SimpleBidBundle) highest; 
      for(Tradeable t : otherBundle.BIDS.keySet()) {
        if(nextHighest.get(t) == null || otherBundle.BIDS.get(t).PRICE > nextHighest.get(t).PRICE) { 
          if (otherBundle.BIDS.get(t).PRICE < bundle.BIDS.get(t).PRICE) {
            nextHighest.put(t, otherBundle.BIDS.get(t)); 
          }
        }
      }
    }
    List<Order> payments = new LinkedList<Order>();
    for(Entry<Tradeable, MarketState> a : nextHighest.entrySet()) {
      payments.add(new Order(a.getValue().AGENTID, null,
          a.getValue().PRICE, 1, new Asset(a.getKey(), 1)));
    }
    state.setPayments(payments);
  }

  @Override
  public void setPaymentType(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReserve(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void permitShort(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }
//	private final Map<Tradeable, MarketState> RESERVE;
//	
//	public SimpleSecondPrice(Map<Tradeable, MarketState> reserve) {
//		if (reserve == null) {
//			this.RESERVE = new HashMap<Tradeable, MarketState>();
//		} else {
//			this.RESERVE = reserve;
//		}
//	}
//	
//	public SimpleSecondPrice() {
//		this.RESERVE = new HashMap<Tradeable, MarketState>();
//	}
//
//	@Override
//	public List<Order> getPayments(MarketInternalState state) {
//		List<Order> orders = new LinkedList<Order>();
//		if (!state.getAllocation().getType().equals(BundleType.Simple)) {
//			Logging.log("[X] Incorrect bundle type " + state.getAllocation().getType());
//			return orders;
//		}
//		SimpleBidBundle alloc = (SimpleBidBundle) state.getAllocation();
//		MarketState def = new MarketState(null,0);
//		for (Asset trade : state.getTradeables()) {
//		  //this is empty
//			MarketState bp = alloc.getBid(trade.getType());
//			if (bp == null || bp.AGENTID == null) {
//				continue;
//			}
//			MarketState current = this.RESERVE.getOrDefault(trade.getType(), def);
//			for (Bid bid : state.getBids()) {
//				if (bid.Bundle.getType().equals(BundleType.Simple)) {
//					SimpleBidBundle bundle = (SimpleBidBundle) bid.Bundle;
//					MarketState otherbid = bundle.getBid(trade.getType());
//					//second price logic
//					//change this at will.
//					if (otherbid != null && otherbid.PRICE > current.PRICE && bp.PRICE > otherbid.PRICE) {
//						current = new MarketState(bp.AGENTID, otherbid.PRICE);
//					}
//				} else {
//					Logging.log("[X] Incorrect bundle type by " + bid.AgentID + " in auction " + bid.AuctionID);
//				}
//			}
//			//System.out.println(current);
//			orders.add(new Order(bp.AGENTID,null,current.PRICE,trade.getCount(),trade));
//		}
//		return orders;
//	}
//
//	@Override
//	public PaymentType getPaymentType() {
//		return PaymentType.SecondPrice;
//	}
//
//	@Override
//	public BidBundle getReserve() {
//		return new SimpleBidBundle(this.RESERVE);
//	}
//
//	@Override
//	public Map<BidBundle, Set<Asset>> getPayments(
//			Map<Integer, Set<Asset>> allocations, Set<Bid> bids) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean permitShort() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//  @Override
//  public List<Order> getPayments(Map<Integer, Set<Asset>> allocations) {
//    // TODO Auto-generated method stub
//    return null;
//  }

}
