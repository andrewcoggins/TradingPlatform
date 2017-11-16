package brown.rules.allocationrules.library; 

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import brown.assets.accounting.Order;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.markets.LemonadeReport;
import brown.rules.allocationrules.AllocationRule;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

public class LemonadeAllocation implements AllocationRule {

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
  public void tick(MarketInternalState state) {
    long time = state.getTime(); 
    time++; 
    state.setTime(time); 
  }
  
  @Override
  public void setAllocation(MarketInternalState state) {
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
        System.out.println("Index " + i + ", closest is " + Math.floorMod(i+r+1,SIZE));
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
      } else if (l<r){
        System.out.println("Index " + i + ", closest is " + Math.floorMod(i-l+1,SIZE));        
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);        
      } else {
        System.out.println("Index " + i + ", closest is " + Math.floorMod(i-l+1,SIZE));                
        System.out.println("Index " + i + ", closest is " + Math.floorMod(i+r+1,SIZE));        
        winners.addAll(slots[Math.floorMod(i+r,SIZE)]);
        winners.addAll(slots[Math.floorMod(i-l,SIZE)]);                
      }
      
      for (int w: winners){ 
        double payoff = numGlasses / winners.size();
        Asset mock = new Asset(new Tradeable(0),0,w);
        Order earned = new Order(w,null,-1 * payoff,1,mock);
        payoffs.add(earned);
      }      
    }    
  state.setPayments(payoffs);
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
  public void setReport(MarketInternalState state) {
    int[] report = new int[this.SIZE];
    for(int i = 0; i < this.SIZE; i++) {
      report[i] = this.slots[i].size();
    }
    state.setReport(new LemonadeReport(report));
  }

  
}