package brown.auction.rules.library; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IPaymentRule;
import brown.logging.Logging;
import brown.mechanism.bidbundle.BundleType;
import brown.mechanism.bidbundle.GameBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.SimpleTradeable;
import brown.platform.accounting.Order;
import brown.platform.messages.TradeMessage;

public class LemonadeGroupedPayment implements IPaymentRule {  
  private int numSlots;
  
  public LemonadeGroupedPayment(int numSlots) {
    this.numSlots = numSlots;
  }

  @Override
  public void setOrders(IMarketState state) {
    List<List<Integer>> grouping = state.getGroups();       

    List<TradeMessage> bids = state.getBids();    
    if(bids.isEmpty()) return;
    
    // Find the number of glasses per slot
    List<ITradeable> tradeables = state.getTradeables();
    List<SimpleTradeable> singleTradeables = new LinkedList<SimpleTradeable>();
    for (ITradeable t : tradeables){
      singleTradeables.addAll(t.flatten());
    }        
    double goodsPerPerson = singleTradeables.size() / this.numSlots / 3.;
    
    // Build orders
    List<Order> orders = new LinkedList<Order>();
    
    for (List<Integer> group : grouping){
      // Glasses Per Slot, normalized to account for size of group
      double glassesPerSlot = goodsPerPerson * group.size();
      
      // Create Array to hold bids in
      @SuppressWarnings("unchecked")
      List<Integer>[] slots = (List<Integer>[]) new List[numSlots];
      for(int i = 0; i < this.numSlots; i++) {
        slots[i] = new LinkedList<Integer>();
      }          
      
      // get bids for just this group
      for (TradeMessage b: bids){
        if (b.Bundle.getType() != BundleType.GAME | !group.contains(b.AgentID))
          continue;
        GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
        int index = lemonadeBid.getBids().move;
        slots[index].add(b.AgentID);
      }

      // For each slot, look for the closest agent
      for (int i = 0; i < this.numSlots; i++) {
        // Search to right
        int r = 0;
        while (slots[Math.floorMod(i+r,this.numSlots)].isEmpty()) {
          r++;
        }
        // Search to left
        int l = 0;      
        while(slots[Math.floorMod(i-l,this.numSlots)].isEmpty()) {
          l++;
        }            
        
        // Person to pay
        List<Integer> winners = new ArrayList<Integer>();
        if (r < l) {        
          winners.addAll(slots[Math.floorMod(i+r,this.numSlots)]);
        } else if (l < r) {     
          winners.addAll(slots[Math.floorMod(i-l,this.numSlots)]);        
        } else {  
          if (Math.floorMod(i+r,this.numSlots) == Math.floorMod(i-l,this.numSlots)){
            winners.addAll(slots[Math.floorMod(i+r,this.numSlots)]);
          } else {
            winners.addAll(slots[Math.floorMod(i+r,this.numSlots)]);
            winners.addAll(slots[Math.floorMod(i-l,this.numSlots)]);                          
          }
        }
        // Logging.log("[L Alloc]: Slot " + i + ", Winners: " + winners);      

        for (int w : winners) { 
          double payment = glassesPerSlot / winners.size();
          orders.add(new Order(w,null,-1*payment,0.0,null));     
        }             
      }      
    }
    state.setPayments(orders);          
  }

  @Override
  public void reset() {
  }
  
}
