package brown.rules.allocationrules.library;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.Allocation;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.BidMessage;
import brown.rules.allocationrules.IAllocationRule;
import brown.tradeable.library.Tradeable;

/**
 * implements an allocation rule where the highest bidder is allocated the good(?)
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocation implements IAllocationRule {

  @Override
  public void tick(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(IMarketState state) {
    Map<Tradeable, MarketState> highest = new HashMap<Tradeable, MarketState>();
    List<BidMessage> allBids = state.getBids();
    for(BidMessage bid : allBids) {
      if(bid.Bundle.getType().equals(BundleType.Simple)) {
        SimpleBidBundle bundle = (SimpleBidBundle) bid.Bundle; 
        for (Tradeable t : bundle.getBids().bids.keySet()) {
          if(highest.get(t) == null || highest.get(t).PRICE < bundle.getBids().bids.get(t).PRICE) { 
            highest.put(t, bundle.getBids().bids.get(t));
          }
        }
      }
    }
    state.setAllocation(new Allocation(new SimpleBid(highest)));
  }

  @Override
  public void setBidRequest(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isOver(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void withReserve(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getAllocationType(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(IMarketState state) {
    // TODO Auto-generated method stub
    
  }
}

