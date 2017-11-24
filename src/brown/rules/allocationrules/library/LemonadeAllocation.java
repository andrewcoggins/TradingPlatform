package brown.rules.allocationrules.library; 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import brown.accounting.BundleType;
import brown.accounting.Order;
import brown.channels.MechanismType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.Bid;
import brown.messages.library.LemonadeReport;
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
  public void tick(IMarketState state) {
    long time = state.getTime(); 
    time++; 
    state.setTime(time); 
  }
  
  @Override
  public void setAllocation(IMarketState state) {
    List<Bid> bids = state.getBids();
    List<Order> payoffs = new ArrayList<Order>();
    
    for (Bid b : bids){
      int index = (int) b.Bundle.getCost();
      slots[index].add(b.AgentID);
    }

    for (int i=0; i<SIZE;i++){
      // Search to right
      int r = 0;
      while(this.slots[Math.floorMod(i+r,SIZE)].isEmpty()){
        r++;
      }
      // Search to left
      int l = 0;      
      while(this.slots[Math.floorMod(i-l,SIZE)].isEmpty()){
        l++;
      }
      
      // Person to pay
      List<Integer> winners = new ArrayList<Integer>();
      if (r<l){
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
      } else if (l<r){     
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);        
      } else { 
        if (i-l != i+r){ 
        }       
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);                
      }
      
      for (int w : winners) { 
        double payoff = numGlasses / winners.size();
        Order earned = new Order(w,null,-1 * payoff,1,new Tradeable(0));
        payoffs.add(earned);
      }      
    }    
  state.setPayments(payoffs);
}

  @Override
  public void setBidRequest(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isPrivate(IMarketState state) {
    state.setPrivate(true); 
    
  }

  @Override
  public void isOver(IMarketState state) {
    long ticks = state.getTime(); 
    if(ticks > 2) { 
      state.setOver(true);
    }
  }

  @Override
  public void setBundleType(IMarketState state) {
    state.setBundleType(BundleType.Simple);
    
  }

  @Override
  public void withReserve(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void isValid(IMarketState state) {
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
  public void getAllocationType(IMarketState state) {
    state.setMType(MechanismType.Lemonade);
  }

  @Override
  public void setReport(IMarketState state) {
    int[] report = new int[this.SIZE];
    for(int i = 0; i < this.SIZE; i++) {
      report[i] = this.slots[i].size();
    }
    state.setReport(new LemonadeReport(report));
  }

  
}