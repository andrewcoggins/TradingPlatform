package brown.auction.rules.library;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import brown.auction.marketstate.library.MarketState;
import brown.mechanism.bid.library.BidType;
import brown.mechanism.bidbundle.library.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.SimpleSealedReportMessage;
import brown.platform.messages.library.TradeMessage;

public class SSSPAnonymousPolicyTest {
  
  @Test
  public void testSSSPAnonymous() {
    List<ITradeable> tList = new LinkedList<ITradeable>();
    tList.add(new SimpleTradeable(0)); 
    MarketState state = new MarketState(0, tList, null); 
    // put agents in group(s)
    List<List<Integer>> groups = new LinkedList<List<Integer>>(); 
    List<Integer> oneGroup = new LinkedList<Integer>(); 
    for (int i= 0; i < 3; i++) {
      // add trade messages
      oneGroup.add(i); 
      Map<ITradeable, BidType> bidMap = new HashMap<ITradeable, BidType>(); 
      bidMap.put(new SimpleTradeable(0), new BidType((double) i, 1)); 
      TradeMessage t = new TradeMessage(0, new AuctionBidBundle(bidMap), 0, i); 
      state.addBid(t);
    }
    groups.add(oneGroup); 
    state.setGroups(groups);
    HighestPriceAllocation allocation = new HighestPriceAllocation(); 
    allocation.setAllocation(state);
   //create rule
    SSSPAnonymousPolicy IRPolicy = new SSSPAnonymousPolicy(); 
    IRPolicy.setReport(state);
    Map<Integer, List<GameReportMessage>> reports = new HashMap<Integer, List<GameReportMessage>>(); 
    for (int i = 0; i < 3; i++) {
      List<GameReportMessage> expectedReports = new LinkedList<GameReportMessage>(); 
      expectedReports.add(new SimpleSealedReportMessage(2, 3)); 
      reports.put(i, expectedReports); 
    }
    assertEquals(state.getReport(), reports); 
  }
  
  public static void main(String[] args) {
    
    SSSPAnonymousPolicyTest s = new SSSPAnonymousPolicyTest(); 
    s.testSSSPAnonymous(); 
    
  }
  
}