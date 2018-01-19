package brown.accounting;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.bid.library.AuctionBid;
import brown.market.marketstate.library.Allocation;
import brown.tradeable.library.MultiTradeable;

/**
 * Test for the Ledger class. 
 * C
 * @author andrew
 *
 */
public class LedgerTest {
  
  @Test
  public void testLedger() {
    Ledger ledgerOne = new Ledger(0); 
    Transaction tranOne = new Transaction(0, 1, 100.0, 1, new MultiTradeable(0));
    Transaction tranTwo = new Transaction(0, 1, 100.0, 1, new MultiTradeable(1));
    ledgerOne.add(tranOne); 
    // test that getList works
    assertEquals(ledgerOne.getList().get(0), tranOne);
    Set<Transaction> transSet = new HashSet<Transaction>();
    transSet.add(tranOne);
    // test that getLatest works
    assertEquals(ledgerOne.getLatest(), transSet);
    //add another element and check for equality
    List<Transaction> tranList = new LinkedList<Transaction>();
    tranList.add(tranOne);
    tranList.add(tranTwo);
    ledgerOne.add(tranTwo);
    assertEquals(ledgerOne.getList(), tranList);
    //and getLatest
    transSet.add(tranTwo);
    assertEquals(ledgerOne.getLatest(), transSet);
    //test getSanitized
    Ledger san = ledgerOne.getSanitized(0);
    Ledger sanCopy = new Ledger(null);
    sanCopy.add(tranOne.sanitize(0));
    sanCopy.add(tranTwo.sanitize(0));
    assertEquals(san, sanCopy);
    //check that clearLatest clears, erm... unshared. 
    ledgerOne.clearLatest();
    Ledger ledgerTwo = ledgerOne.getSanitized(0);
    Ledger ledgerThree = new Ledger(null);
    assertEquals(ledgerTwo, ledgerThree);
    // now test the allocation parts. 
    Map<MultiTradeable, MarketState> m = new HashMap<MultiTradeable, MarketState>();
    m.put(new MultiTradeable(0), new MarketState(1, 1.0));
    AuctionBid s = new AuctionBid(m);
    Allocation al = new Allocation(s);
    Ledger ledgerFour = new Ledger(0, al);
    List<Transaction> transacts = new LinkedList<Transaction>();
    for(Entry<MultiTradeable, MarketState> a : m.entrySet()) {
      transacts.add(new Transaction(a.getValue().AGENTID,
          null, a.getValue().PRICE, 1, a.getKey()));
    }
    assertEquals(ledgerFour.getList(), transacts);
  }
  // works for me. Still: what to do in the complex case? 
}