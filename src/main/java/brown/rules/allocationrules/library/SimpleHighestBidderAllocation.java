package brown.rules.allocationrules.library;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abrown.misc.library.Allocation;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.allocationrules.IAllocationRule;
import brown.tradeable.library.MultiTradeable;

/**
 * implements an allocation rule where the highest bidder is allocated the good(?)
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocation implements IAllocationRule {

  @Override
  public void tick(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setAllocation(ICompleteState state) {
    Map<MultiTradeable, Double> highest = new HashMap<MultiTradeable, Double>();
    List<TradeMessage> allBids = state.getBids();
    for(TradeMessage bid : allBids) {
      if(bid.Bundle.getType().equals(BundleType.Simple)) {
        SimpleBidBundle bundle = (SimpleBidBundle) bid.Bundle; 
        for (MultiTradeable t : bundle.getBids().bids.keySet()) {
          if(highest.get(t) == null || highest.get(t) < bundle.getBids().bids.get(t)) { 
            highest.put(t, bundle.getBids().bids.get(t));
          }
        }
      }
    }
    state.setAllocation(new Allocation(new SimpleBid(highest)));
  }

  @Override
  public void setBidRequest(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isOver(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setBundleType(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void withReserve(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void getAllocationType(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setReport(ICompleteState state) {
    // TODO Auto-generated method stub
    
  }
}

