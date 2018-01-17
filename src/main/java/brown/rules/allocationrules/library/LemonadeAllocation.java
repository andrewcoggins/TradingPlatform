package brown.rules.allocationrules.library; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.LemonadeBidBundle;
import brown.channels.MechanismType;
import brown.market.marketstate.ICompleteState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.rules.allocationrules.IAllocationRule;
import brown.tradeable.library.Tradeable;

public class LemonadeAllocation implements IAllocationRule {

  private Integer SIZE = 12;
  private double numGlasses = 2.0;
  private List<Integer>[] slots;
  
  @SuppressWarnings("unchecked")
  public LemonadeAllocation() {
    this.slots = (List<Integer>[]) new List[SIZE];
    for(int i = 0; i < SIZE; i++) {
      slots[i] = new LinkedList<Integer>();
    }
  }   
  
  @Override
  public void tick(ICompleteState state) {
    long time = state.getTime(); 
    time++; 
    state.setTime(time); 
  }
  
  @Override
  public void setAllocation(ICompleteState state) {
    List<TradeMessage> bids = state.getBids();
    if(bids.isEmpty()) return;
    List<Order> payoffs = new ArrayList<Order>();
    
    for (TradeMessage b : bids) {
      if (b.Bundle.getType() != BundleType.Lemonade)
        continue;
      LemonadeBidBundle lemonadeBid = (LemonadeBidBundle) b.Bundle;
      int index = lemonadeBid.getBids().bid;
      slots[index].add(b.AgentID);
    }

    for (int i = 0; i < SIZE; i++) {
      // Search to right
      int r = 0;
      while(this.slots[Math.floorMod(i+r,SIZE)].isEmpty()) {
        r++;
      }
      // Search to left
      int l = 0;      
      while(this.slots[Math.floorMod(i-l,SIZE)].isEmpty()) {
        l++;
      }
      
      // Person to pay
      List<Integer> winners = new ArrayList<Integer>();
      if (r < l) {
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
      } else if (l < r) {     
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);        
      } else { 
        if (i-l != i+r) { 
        }       
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);                
      }
      
      for (int w : winners) { 
        double payoff = numGlasses / winners.size();
        // System.out.println(payoff);
        Order earned = new Order(w, null, -1 * payoff, 1, new Tradeable(0));
        payoffs.add(earned);
      }      
    } 
    
    // hacky and bad.
    // actually it may not be. 
    // The thing being 'allocated' in this game is just money itself. 
    // So if the allocation rule deals with who gets what good and a payment rule 
    // involves mapping this allocation to a payment scheme, than the payment rule 
    // really does nothing in this game,
    // because the allocation and payment scheme are the same.
    state.setPayments(payoffs);
  }

  @Override
  public void setBidRequest(ICompleteState state) {
    // TODO Auto-generated method stub  
  }

  @Override
  public void isPrivate(ICompleteState state) {
    state.setPrivate(true); 
  }

  @Override
  public void isOver(ICompleteState state) {
    long ticks = state.getTime(); 
    if (ticks > 2) { 
      state.setOver(true);
    }
  }

  @Override
  public void setBundleType(ICompleteState state) {
    state.setBundleType(BundleType.Simple);
  }

  @Override
  public void withReserve(ICompleteState state) {
    // TODO Auto-generated method stub
  }

  @Override
  public void isValid(ICompleteState state) {
    
  }

  @Override
  public void getAllocationType(ICompleteState state) {
    state.setMType(MechanismType.Lemonade);
  }

  @Override
  public void setReport(ICompleteState state) {
    int[] report = new int[this.SIZE];
    for(int i = 0; i < this.SIZE; i++) {
      report[i] = this.slots[i].size();
    }
    state.setReport(new LemonadeReportMessage(report));
  }
  
}