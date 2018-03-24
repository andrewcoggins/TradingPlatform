package brown.rules.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

import ch.uzh.ifi.ce.mweiss.specvalopt.vcg.external.domain.AuctionResult;
import brown.bid.interim.BidType;
import brown.bidbundle.BundleType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IAllocationRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.tradeable.library.TradeableType;
import brown.value.generator.library.SpecValGenerator;

public class VCGAllocation implements IAllocationRule {

  @Override
  public void setAllocation(IMarketState state) {
    // convert all sent bids to vcg solver form
    List<TradeMessage> bids = state.getBids(); 
    Map<String, Map<Set<String>, Double>> vcgFormatted = new HashMap<String, Map<Set<String>, Double>>(); 
    for (TradeMessage trade : bids) {
      if (trade.Bundle.getType() != BundleType.AUCTION) {
        continue; 
      }
      Map<Set<String>, Double> formattedCBid = new HashMap<Set<String>, Double>(); 
      AuctionBidBundle tradeBundle = (AuctionBidBundle) trade.Bundle; 
      Map<ITradeable, BidType> bidMap = tradeBundle.getBids().bids;
      for(Entry<ITradeable, BidType> e : bidMap.entrySet()) {
        if (e.getKey().getType() != TradeableType.Complex){ 
          continue; 
        }
        ComplexTradeable cTrade = (ComplexTradeable) e.getKey();
        Double bidAmt = e.getValue().price;
        // annoying conversion
        List<SimpleTradeable> simples = cTrade.flatten();
        Set<String> formattedTradeables = new HashSet<String>();
        for(SimpleTradeable s : simples) {
          formattedTradeables.add(s.ID.toString()); 
        }
        formattedCBid.put(formattedTradeables, bidAmt);
      }
      vcgFormatted.put(trade.AgentID.toString(), formattedCBid);
    }
    // use vcg solver (specval initialization doesn't really matter)
    SpecValGenerator svg = new SpecValGenerator(bids.size(), 1);
    AuctionResult solvedVCG = svg.runVCGWithGivenBids(vcgFormatted);
    //convert AuctionResult to usable form. 
    Map<String, SortedSet<String>> alloc = svg.getVcgAllocationSimpleBid(solvedVCG); 
    Map<Integer, List<ITradeable>> stateFormattedAlloc = new HashMap<Integer, List<ITradeable>>();
    for(Entry<String, SortedSet<String>> a : alloc.entrySet()) {
      Integer agentID = Integer.valueOf(a.getKey());
      List<ITradeable> allocated = new LinkedList<ITradeable>();
      for (String s : a.getValue()) {
        SimpleTradeable aTradeable = new SimpleTradeable(Integer.valueOf(s)); 
        allocated.add(aTradeable); 
      }
      stateFormattedAlloc.put(agentID, allocated);
    }
    state.setAllocation(stateFormattedAlloc);
  }

  @Override
  public void reset() {
    
  }
  
  
}