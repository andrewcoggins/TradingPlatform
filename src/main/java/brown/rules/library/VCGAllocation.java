package brown.rules.library; 

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;

import ch.uzh.ifi.ce.mweiss.specval.model.UnsupportedBiddingLanguageException;
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
    
    Map<Integer, Integer> specToPlatform = new HashMap<Integer, Integer>(); 
    // agglomerate all trade messages.
    Map<Integer, Map<ITradeable, BidType>> completes = new HashMap<Integer, Map<ITradeable, BidType>>(); 
    List<TradeMessage> bids = state.getBids();
    for (TradeMessage bid : bids) {
      Integer ID = bid.AgentID; 
      if(completes.get(ID) != null) { 
        Map<ITradeable, BidType> existing = completes.get(ID); 
        AuctionBidBundle auctionBundle = (AuctionBidBundle) bid.Bundle; 
        Map<ITradeable, BidType> abMap = auctionBundle.getBids().bids; 
        for (ITradeable tradeable : abMap.keySet()) { 
          existing.put(tradeable, abMap.get(tradeable)); 
        }
        completes.put(ID, existing); 
      }
      else {
        AuctionBidBundle auctionBundle = (AuctionBidBundle) bid.Bundle; 
        Map<ITradeable, BidType> abMap = auctionBundle.getBids().bids; 
        completes.put(ID, abMap);
      }
    }
    // get into vcg solver form.
    Map<String, Map<Set<String>, Double>> vcgFormatted = new HashMap<String, Map<Set<String>, Double>>(); 
    int i = 0; 
    for (Entry<Integer, Map<ITradeable, BidType>> agentBid : completes.entrySet()) {
      Integer agentID = agentBid.getKey(); 
      Integer vcgAgent = i; 
      specToPlatform.put(vcgAgent, agentID); 
      Map<ITradeable, BidType> aBid = agentBid.getValue(); 
      Map<Set<String>, Double> specBid = new HashMap<Set<String>, Double>(); 
      for(Entry<ITradeable, BidType> atom : aBid.entrySet()){ 
        Set<String> specTradeables = new HashSet<String>(); 
        ComplexTradeable cTradeable = (ComplexTradeable) atom.getKey(); 
        for(SimpleTradeable t : cTradeable.flatten()) {
          specTradeables.add(t.ID.toString());
        }
        Double bidAmt = atom.getValue().price; 
        specBid.put(specTradeables, bidAmt); 
      }
      vcgFormatted.put(vcgAgent.toString(), specBid); 
      i++;
    }
    // use vcg solver (specval initialization doesn't really matter)
    SpecValGenerator svg = new SpecValGenerator(vcgFormatted.size(), 1);
    try {
      svg.makeValuations();
    } catch (UnsupportedBiddingLanguageException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
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