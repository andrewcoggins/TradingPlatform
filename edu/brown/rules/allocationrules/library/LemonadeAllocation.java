package brown.rules.allocationrules.library; 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


import brown.assets.accounting.Order;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.markets.LemonadeReport;
import brown.rules.allocationrules.AllocationRule;
import brown.tradeables.Asset;

public class LemonadeAllocation implements AllocationRule {

  private Integer SIZE = 12;
  private int[] slots;
  
  @Override
  public void tick(MarketInternalState state) {
    long time = state.getTime(); 
    time++; 
    state.setTime(time); 
  }

  @Override
  public void setAllocation(MarketInternalState state) {
    //create a list of the people in the game at each position.
    List<Bid> bids = state.getBids();
    Set<Asset> items = state.getTradeables();
    List<Order> payoffs = new ArrayList<Order>();
    @SuppressWarnings("unchecked")
    List<Integer>[] positions = (List<Integer>[]) new List[SIZE];
    for(Bid bid : bids) {
      Integer place = (int) bid.Bundle.getCost() - 1;
      if(place >= 0 && place < 12) {
        if (positions[place] == null) {
          positions[place] = new LinkedList<Integer>();
        }
        positions[place].add(bid.AgentID);
       }  
    }
    //now run the logic of the game. 
    for (int i = 0; i < SIZE; i++) {
      //report on the status of the game.
      if (positions[i] == null) {
        continue;
      }
      else {
        this.slots[i] = positions[i].size();
      }
      //calculate payoffs based on nearest positions.
      double payoff = 0;

      int before = i;
      int after = i;
      for (int next = (i == 11 ? 0 : i+1); next != i; next++) {
        if (positions[next] !=  null) {
          if (after == i) {
            after = next;
          }
          before = next;
        }
        
        if (next == 11) {
          next = -1;
        }
      }
      payoff = after > i ? after - i : 11-i + after;
      payoff += before < i ? i - before : before;
      payoff /= (double) positions[i].size();
      //give people the payments. 
      
      for (Integer person : positions[i]) {
        Order earned = new Order(person, null, payoff, 1, null);
        payoffs.add(earned); 
      }
      state.setPayments(payoffs);
    }
  }

  @Override
  public void setBidRequest(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(MarketInternalState state) {
    state.setPrivate(true); 
    
  }

  @Override
  public void isOver(MarketInternalState state) {
    long ticks = state.getTime(); 
    if(ticks > 2) { 
      state.setOver(true);
    }
  }

  @Override
  public void setBundleType(MarketInternalState state) {
    state.setBundleType(BundleType.Simple);
    
  }

  @Override
  public void withReserve(MarketInternalState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(MarketInternalState state) {
//    // TODO Auto-generated method stub
//    if (bid.AgentID == null || bid.Bundle == null
//        || bid.Bundle.getCost() < 1 || bid.Bundle.getCost() > 12) {
//      state.setValid(false);
//    }
//    
//    for (Bid b : bids) {
//      if (b.AgentID.equals(bid.AgentID)) {
//        state.setValid(false);
//      }
//    }
//    
//    return true;
    }

  @Override
  public void getAllocationType(MarketInternalState state) {
    state.setMType(MechanismType.Lemonade);
  }

  @Override
  public void getReport(MarketInternalState state) {
    state.setReport(new LemonadeReport());
  }

  
}