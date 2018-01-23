package brown.rules.library; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IAllocationRule;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;

public class LemonadeAllocation implements IAllocationRule {
  private int numSlots;
  private List<Integer>[] slots;
  
  @SuppressWarnings("unchecked")
  public LemonadeAllocation(int numSlots) {
    this.numSlots = numSlots;
    this.slots = (List<Integer>[]) new List[numSlots];
    for(int i = 0; i < this.numSlots; i++) {
      slots[i] = new LinkedList<Integer>();
    }    
  }

  @Override
  public void setAllocation(IMarketState state) {
    List<TradeMessage> bids = state.getBids();
    if(bids.isEmpty()) return;
     
    // Find the number of glasses per slot
    List<ITradeable> tradeables = state.getTradeables();
    List<SimpleTradeable> singleTradeables = new LinkedList<SimpleTradeable>();
    for (ITradeable t : tradeables){
      singleTradeables.addAll(t.flatten());
    }
    
    double glassesPerSlot = singleTradeables.size() / this.numSlots;

    // Put agents where they bid
    Map<Integer, List<ITradeable>> alloc = new HashMap<Integer,List<ITradeable>>();
    for (TradeMessage b : bids) {
      if (b.Bundle.getType() != BundleType.GAME)
        continue;
      GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
      int index = lemonadeBid.getBids().move;
      slots[index].add(b.AgentID);
    }

    for (int i = 0; i < this.numSlots; i++) {
      // Search to right
      int r = 0;
      while(this.slots[Math.floorMod(i+r,this.numSlots)].isEmpty()) {
        r++;
      }
      // Search to left
      int l = 0;      
      while(this.slots[Math.floorMod(i-l,this.numSlots)].isEmpty()) {
        l++;
      }            
      
      // Person to pay
      List<Integer> winners = new ArrayList<Integer>();
      if (r < l) {        
        winners.addAll(slots[Math.floorMod(i+r,this.numSlots)]);
      } else if (l < r) {     
        winners.addAll(slots[Math.floorMod(i-l,this.numSlots)]);        
      } else { 
        winners.addAll(slots[Math.floorMod(i+r,this.numSlots)]);
        winners.addAll(slots[Math.floorMod(i-l,this.numSlots)]);                
      }
      
      for (int w : winners) { 
        int numGlasses = (int) Math.floor(glassesPerSlot / winners.size());
        List<ITradeable> curr =  alloc.getOrDefault(w, new LinkedList<ITradeable>());
        
        // manage tradeables in state?
        for (int z = 0;z<numGlasses;z++){
          if (singleTradeables.size() == 0){
            Logging.log("ALLOCATION RULE: OVERALLOCATED TRADEABLE");
            break;
          }
          curr.add(singleTradeables.remove(0));        
        }        
        alloc.put(w, curr);
      }      
      
      state.setAllocation(alloc);
    } 
  }

  @SuppressWarnings("unchecked")
  @Override
  public void reset() {
    this.slots = (List<Integer>[]) new List[this.numSlots];
    for(int i = 0; i < this.numSlots; i++) {
      slots[i] = new LinkedList<Integer>();
    }
  }  
}
