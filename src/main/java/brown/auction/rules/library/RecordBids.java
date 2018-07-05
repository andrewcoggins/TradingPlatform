package brown.auction.rules.library;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IRecordKeepingRule;
import brown.auction.value.valuation.IValuation;
import brown.logging.Logging;
import brown.mechanism.bid.BidType;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.TradeMessage;

public class RecordBids implements IRecordKeepingRule {
  private final String path;
  
  public RecordBids(String path) {
    this.path = path;
  }
  
 
  @Override
  public void record(IMarketState state, Map<Integer,IValuation> privateVals) throws IOException {
    Logging.log("Record: " + this.path);
    PrintWriter out = new PrintWriter(new FileWriter(this.path, true));
    for (TradeMessage tm : state.getBids()) {
      AuctionBidBundle auctionBid = (AuctionBidBundle) tm.Bundle;
      for (Entry<ITradeable,BidType> bid: auctionBid.getBids().bids.entrySet())
        out.println(tm.AgentID + ", " + bid.getValue().price + ", " + privateVals.get(tm.AgentID).getValuation(bid.getKey()));            
    }
    out.close();    
  }
}
