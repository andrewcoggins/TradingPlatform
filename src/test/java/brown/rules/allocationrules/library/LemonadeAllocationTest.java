package brown.rules.allocationrules.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import brown.accounting.Account;
import brown.accounting.bidbundle.library.GameBidBundle;
import brown.market.marketstate.library.CompleteState;
import brown.market.marketstate.library.Order;
import brown.messages.library.TradeMessage;
import brown.tradeable.library.MultiTradeable;

/**
 * test the lemonade allocation 
 * I
 * @author andrew
 *
 */
public class LemonadeAllocationTest { 
  
  /*
   * basic tests.
   */
  @Test 
  public void testLemonadeAllocation() {
    Account agentAccount = new Account(0);
    //create lemonade allocation
    LemonadeAllocation lem = new LemonadeAllocation();
    //create internal state.
    Set<MultiTradeable> allTradeables = new HashSet<MultiTradeable>();
    for(int i = 0; i < 3; i++) {
     allTradeables.add(new MultiTradeable(i));
    }
    CompleteState state = new CompleteState(0, allTradeables);
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
    TradeMessage aBid = new TradeMessage(1, new GameBidBundle(1), 1, 1);
    state.addBid(aBid);
    lem.setAllocation(state);
    for (Order o : state.getPayments()) {
      agentAccount.add(-1 * o.PRICE);
    }
    assertTrue(agentAccount.getMonies() == 24.0);
    agentAccount.add(-24.0);
    //add another player and test again. 
    CompleteState stateTwo = new CompleteState(0, allTradeables);
    Account anotherAgentAccount = new Account(2);
    TradeMessage secondBid = new TradeMessage(2, new GameBidBundle(2), 1, 2);
    stateTwo.addBid(aBid);
    stateTwo.addBid(secondBid);
    lem.setAllocation(stateTwo);
    for(Order o : stateTwo.getPayments()) {
      if (o.TO == 1) {
        agentAccount.add(-1 * o.PRICE);
      } else if (o.TO == 2) {
        anotherAgentAccount.add(-1 * o.PRICE);
      }
    }
    assertTrue(agentAccount.getMonies() == 12.0);
    assertTrue(agentAccount.getMonies() == 12.0);
  }
  
  /*
   * test with one agent on every space. 
   */
  @Test
  public void testLemonadeAllocationAgain() {
    LemonadeAllocation lem = new LemonadeAllocation();
    List<Account> accounts = new LinkedList<Account>();
    for(int i = 0; i < 12; i++) { 
      accounts.add(new Account(i));
    }
    CompleteState state = new CompleteState(0, null);
    List<TradeMessage> allBids = new LinkedList<TradeMessage>();
    for(int i = 0; i < 12; i++) {
      state.addBid(new TradeMessage(i, new GameBidBundle(i), 1, i));
    }
    lem.setAllocation(state);
    for (Order o : state.getPayments()) {
      accounts.get(o.TO).add(-1 * o.PRICE);
    }
    for(int i = 0; i < 12; i++) {
      assertTrue(accounts.get(i).getMonies() == 2.0);
    }
  }
  
  /*
   * test with multiple agents on particular spaces.
   */
  @Test
  public void testLemonadeAllocationThree() {
    LemonadeAllocation lem = new LemonadeAllocation();
    List<Account> accounts = new LinkedList<Account>();
    for (int i = 0; i < 12; i++) {
      accounts.add(new Account(i));
    }
    CompleteState state = new CompleteState(0, null);
    for(int i = 0; i < 12; i++) {
      if (i < 3) state.addBid(new TradeMessage(i, new GameBidBundle(0), 1, i));
      else if (i >= 3 && i < 6) state.addBid(new TradeMessage(i, new GameBidBundle(3), 1, i));
      else if (i >= 6 && i < 9) state.addBid(new TradeMessage(i, new GameBidBundle(6), 1, i));
      else state.addBid(new TradeMessage(i, new GameBidBundle(9), 1, i));
    }
    lem.setAllocation(state);
    for (Order o : state.getPayments()) {
      accounts.get(o.TO).add(-1 * o.PRICE);
    }
    for(int i = 0; i < 12; i++) {
      assertTrue(accounts.get(i).getMonies() == 2.0);
    }
  }
  
  /*
   * something else...
   */
  @Test
  public void testLemonadeAllocationFour() {
    
  }
}