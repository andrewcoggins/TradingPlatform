package brown.rules.allocationrules.library;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.bundles.BundleType;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.rules.allocationrules.AllocationRule;
import brown.valuable.library.Tradeable;

/**
 * implements an allocation rule where the highest bidder is allocated the good(?)
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocation implements AllocationRule {

  @Override
  public void tick(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(MarketInternalState state) {
    Map<Tradeable, MarketState> highest = new HashMap<Tradeable, MarketState>();
    List<Bid> allBids = state.getBids();
    for(Bid bid : allBids) {
      if(bid.Bundle.getType().equals(BundleType.Simple)) {
        SimpleBidBundle bundle = (SimpleBidBundle) bid.Bundle; 
        for (Tradeable t : bundle.BIDS.keySet()) {
          if(highest.get(t) == null || highest.get(t).PRICE < bundle.BIDS.get(t).PRICE) { 
            highest.put(t, bundle.BIDS.get(t));
          }
        }
      }
    }
    state.setAllocation(new SimpleBidBundle(highest));
  }

  @Override
  public void setBidRequest(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isOver(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void withReserve(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getAllocationType(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }
}

