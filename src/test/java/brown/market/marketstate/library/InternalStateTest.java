package brown.market.marketstate.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import brown.accounting.BundleType;
import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.Order;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.Allocation;
import brown.accounting.bidbundle.library.ComplexBidBundle;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.LemonadeChannel;
import brown.messages.library.TradeMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.TradeRequestMessage;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;

/**
 * tests the internal state class. 
 * C
 * issues: 
 * get/set Reserve Bundle v get/set BundleReserve (can consolidate?)
 * IR policy
 * 
 * @author andrew
 *
 */
public class InternalStateTest {
  
  @Test
  public void testInternalState() {
    Set<Tradeable> tradeables = new HashSet<Tradeable>();
    tradeables.add(new Tradeable(0));
    tradeables.add(new Tradeable(1));
    tradeables.add(new Tradeable(2));
    InternalState state = new InternalState(0, tradeables);
    //going to go through these in order to the best of my ability. 
    Map<Tradeable, MarketState> map = new HashMap<Tradeable, MarketState>();
    map.put(new Tradeable(1), new MarketState(0, 1.0));
    TradeMessage aBid = new TradeMessage(0, new SimpleBidBundle(map), 0, 0);
    //addBid, getBids
    state.addBid(aBid);
    assertEquals((SimpleBid) state.getBids().get(0).Bundle.getBids(), new SimpleBid(map));
    //clear
    state.clear();
    assertEquals(state.getBids().size(), 0);
    //getTradeables
    assertEquals(state.getTradeables(), tradeables);
    //getID
    assertEquals(state.getID(), new Integer(0));
    //setLastPayments, getLastPayments
    List<Order> lastPayments = new LinkedList<Order>();
    lastPayments.add(new Order(1, 0, 100.0, 5, new Tradeable(2)));
    state.setLastPayments(lastPayments);
    assertEquals(state.getLastPayments(), lastPayments);
    //tick, getTicks
    state.tick((long) 0.0);
    assertTrue(state.getTicks() == 1); 
    //setReserve, getBundleReserve
    SimpleBidBundle aBundle = new SimpleBidBundle(map);
    state.setReserve(aBundle);
    SimpleBidBundle gottenBundle = (SimpleBidBundle) state.getbundleReserve();
    assertEquals(aBundle, gottenBundle);
    //complex case
    Map<Set<Tradeable>, MarketState> complexMap = new HashMap<Set<Tradeable>, MarketState>();
    complexMap.put(tradeables, new MarketState(0, 1.0));
    ComplexBidBundle comMap = new ComplexBidBundle(complexMap, 0);
    state.setReserve(comMap); 
    ComplexBidBundle gottenCom = (ComplexBidBundle) state.getbundleReserve();
    assertEquals(comMap, gottenCom);
    //clearBids
    state.addBid(aBid);
    state.clearBids();
    assertTrue(state.getBids().size() == 0);
    //getIncrement
    assertTrue(state.getIncrement() == 20.0);
    //setMaximizingRevenue, isMaximizingRevenue
    state.setMaximizingRevenue(true);
    assertTrue(state.isMaximizingRevenue());
    state.setMaximizingRevenue(false);
    assertTrue(!state.isMaximizingRevenue());
    //getEligibility (appears to not be functional)
    assertTrue(state.getEligibility() == 0);
    // allocation rules.
    // get/set time
    state.setTime((long) 1.0);
    assertTrue(state.getTime() == (long) 1.0);
    //get/set allocation
    Map<Tradeable, MarketState> allMap = new HashMap<Tradeable, MarketState>();
    allMap.put(new Tradeable(0), new MarketState(1, 2.0));
    Allocation a = new Allocation(new SimpleBid(allMap));
    state.setAllocation(a);
    assertEquals(a, state.getAllocation());
    //get/set bidRequestMessage
    BidRequestMessage b = new BidRequestMessage(0, 0, BundleType.Complex, comMap, tradeables);
    state.setRequest(b);
    assertEquals(state.getRequest(), b); 
    //get/set bundleType
    BundleType bType = BundleType.Simple;
    state.setBundleType(bType);
    assertEquals(state.getBundleType(), bType);
    //get/set reserve.
    Set<TradeMessage> reserve = new HashSet<TradeMessage>();
    reserve.add(new TradeMessage(0, aBundle, 1, 1));
    state.setReserve(reserve);
    assertEquals(reserve, state.getReserve());
    //get/set valid
    state.setValid(true); 
    assertTrue(state.getValid());
    state.setValid(false);
    assertTrue(!state.getValid());
    //get/setMType
    state.setMType(MechanismType.SealedBid);
    assertEquals(state.getMType(), MechanismType.SealedBid);
    state.setMType(MechanismType.OpenOutcry);
    assertEquals(state.getMType(), MechanismType.OpenOutcry);
    //get/set report
    Ledger aLedger = new Ledger(1);
    GameReportMessage aMessage = new GameReportMessage(aLedger);
    state.setReport(aMessage);
    assertEquals(state.getReport(), aMessage);
    //payment rules. 
    //set/getPayments
    List<Order> someOrders = new LinkedList<Order>();
    someOrders.add(new Order(0, 1, 100.0, 5, new Tradeable(0)));
    state.setPayments(someOrders);
    assertEquals(state.getPayments(), someOrders);
    //get set paymentType
    state.setPaymentType(PaymentType.SecondPrice);
    assertEquals(state.getPaymentType(), PaymentType.SecondPrice);
    state.setPaymentType(PaymentType.FirstPrice);
    assertEquals(state.getPaymentType(), PaymentType.FirstPrice);
    //get set reserve bundle
    state.setReserveBundle(aBundle);
    assertEquals(state.getReserveBundle(), aBundle);
    //get set short
    state.setShort(true);
    assertTrue(state.permitShort());
    state.setShort(false);
    assertTrue(!state.permitShort());
    //query rules
    //get/set TRequest
    TradeRequestMessage tMessge = new TradeRequestMessage(0,
        new LemonadeChannel(0, new Ledger(1), PaymentType.Lemonade, MechanismType.Lemonade), MechanismType.Lemonade);
    state.setTRequest(tMessge);
    assertEquals(state.getTRequest(), tMessge);
    //activity rules
    //get/set acceptable
    state.setAcceptable(false);
    assertTrue(!state.getAcceptable());
    state.setAcceptable(true);
    assertTrue(state.getAcceptable());
    //termination conditions
    //innerOver
    state.setInnerOver(false);
    assertTrue(!state.getInnerOver());
    state.setInnerOver(true);
    assertTrue(state.getInnerOver());
    //outerOver
    state.setOuterOver(true);
    assertTrue(state.getOuterOver());
    state.setOuterOver(false);
    assertTrue(!state.getOuterOver());
    //outerRuns
    for(int i = 0; i < 7; i++) {
      state.incrementOuter();
    }
    assertTrue(state.getOuterRuns() == 7);
  }
}