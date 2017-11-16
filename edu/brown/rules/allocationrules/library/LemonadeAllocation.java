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
  //12 people, 2 glasses each. 
  //
//  @Override
//  public void setAllocation(MarketInternalState state) {
//    //create a list of the people in the game at each position.
//    List<Bid> bids = state.getBids();
//    List<Order> payoffs = new ArrayList<Order>();
//    @SuppressWarnings("unchecked")
//    List<Integer>[] positions = (List<Integer>[]) new List[SIZE];
//    for(Bid bid : bids) {
//      Integer place = (int) bid.Bundle.getCost() - 1;
//      if(place >= 0 && place < SIZE) {
//        if (positions[place] == null) {
//          positions[place] = new LinkedList<Integer>();
//        }
//        positions[place].add(bid.AgentID);
//       }  
//    }
//    //now run the logic of the game. 
//    for (int i = 0; i < SIZE; i++) { 
//      //report on the status of the game.
//      if (positions[i] == null) {
//        continue;
//      }
//      else {
//        this.slots[i] = positions[i].size();
//      }
//      //calculate payoffs based on nearest positions.
//      double payoff = 0;
//
//      int before = i;
//      int after = i;
//      for (int next = (i == (SIZE - 1) ? 0 : i+1); next != i; next++) {
//        if (positions[next] !=  null) {
//          if (after == i) {
//            after = next;
//          }
//          before = next;
//        }
//        
//        if (next == SIZE - 1) {
//          next = -1;
//        }
//      }
//      payoff = after > i ? after - i : (SIZE - 1) - i + after;
//      payoff += before < i ? i - before : before;
//      payoff /= (double) positions[i].size();
//      //give people the payments. 
//      
//      for (Integer person : positions[i]) {
//        Asset mock = new Asset(new Tradeable(0), 0, person);
//        Order earned = new Order(person, null, -1 * payoff, 1, mock);
//        payoffs.add(earned); 
//      }
//      state.setPayments(payoffs);
//    }
//  }

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