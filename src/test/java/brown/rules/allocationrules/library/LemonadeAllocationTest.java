package brown.rules.allocationrules.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.accounting.Order;
import brown.accounting.bidbundle.library.LemonadeBidBundle;
import brown.market.marketstate.library.InternalState;
import brown.messages.library.BidMessage;
import brown.tradeable.library.Tradeable;

/**
 * test the lemonade allocation 
 * I
 * @author andrew
 *
 */
public class LemonadeAllocationTest { 
  
  @Test 
  public void testLemonadeAllocation() {
    //create lemonade allocation
    LemonadeAllocation lem = new LemonadeAllocation();
    //create internal state.
    Set<Tradeable> allTradeables = new HashSet<Tradeable>();
    for(int i = 0; i < 3; i++) {
     allTradeables.add(new Tradeable(i));
    }
    InternalState state = new InternalState(0, allTradeables);
    //test tick
    lem.tick(state);
    assertTrue(state.getTime() == (long) 1.0);
    lem.tick(state);
    assertTrue(state.getTime() == (long) 2.0);
    //test setAllocation
    //finally some actual testing!
    //in the case where there are no bids.
    //lem.setAllocation(state);
    //assertEquals(state.getAllocation(), null);
    //one bid. 
    BidMessage aBid = new BidMessage(1, new LemonadeBidBundle(1), 1, 1);
    state.addBid(aBid);
    lem.setAllocation(state);
    assertEquals(state.getPayments().get(0), new Order(1, null, -2.0, 1, new Tradeable(0)));
    //a larger game
//    LemonadeAllocation lemTwo = new LemonadeAllocation();
//    InternalState stateTwo = new InternalState(0, allTradeables);
//    BidMessage bidTwo = new BidMessage(2, new LemonadeBidBundle(2), 1, 1);
//    stateTwo.addBid(aBid);
//    stateTwo.addBid(bidTwo);
//    lemTwo.setAllocation(stateTwo);
//    List<Order> payments = new LinkedList<Order>();
//    payments.add(new Order(1, null, -1.0, 1, new Tradeable(0)));
//    payments.add(new Order(2, null, -1.0, 1, new Tradeable(0)));
//    assertEquals(stateTwo.getPayments(), payments);
   
    
  }
}