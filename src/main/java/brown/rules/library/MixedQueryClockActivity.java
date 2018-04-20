package brown.rules.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bid.library.QueryBid;
import brown.bidbundle.BundleType;
import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.tradeable.library.SimpleTradeable;

public class MixedQueryClockActivity implements IActivityRule {
  private final Double increment; 
  
  public MixedQueryClockActivity(Double anIncrement) {
    this.increment = anIncrement; 
  }

  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    if (state.getTicks() % 2 == 1){
      boolean acceptable = true;
      if (aBid.Bundle.getType() == BundleType.QUERY){
        QueryBid bid = (QueryBid) aBid.Bundle.getBids();
        for (ComplexTradeable ct: bid.bundles){
          for (SimpleTradeable st : ct.flatten()){
            if (st.ID<0 || st.ID>99){
              acceptable = false;
            }
          }
        }

      } else {
        acceptable = false;
      }
      // do clock  
            
      state.setAcceptable(acceptable);
    } else {
      if (aBid.Bundle.getType() == BundleType.AUCTION){
        AuctionBid bid = (AuctionBid) aBid.Bundle.getBids();
        double sumOfBids = 0;
        double sumOfReserves = 0;
        
        boolean acceptable = true; 
        
        for (Entry<ITradeable,BidType> entry: bid.bids.entrySet()){
          List<SimpleTradeable> stList = entry.getKey().flatten();
          if (stList.size() > 1){            
            acceptable = false; 
            break;
          }
          for (SimpleTradeable st: stList){
            if (st.ID<0 || st.ID>99){
              acceptable = false;
            }
          }
          sumOfBids = sumOfBids + entry.getValue().price;
          sumOfReserves = sumOfReserves + state.getReserve().get(entry.getKey());
        }      
        if (sumOfBids < sumOfReserves){
          acceptable = false;
        }
        state.setAcceptable(acceptable);
      } else {
        Logging.log("Wrong type of bid in auction phase");
        state.setAcceptable(false);
      }
    }
  }

  @Override
  public void setReserves(IMarketState state) {
    if (state.getFlatIncrement()==0) {
      state.setFlatIncrement(this.increment);
    } else {      
      if (state.getTicks() % 2 == 0){
        Map<ITradeable, List<Integer>> alloc = state.getAltAlloc();
        Map<ITradeable, Double> currReserve = state.getReserve();
        Map<ITradeable, Double> newReserve = new HashMap<ITradeable, Double>();
        
        for (ITradeable t : state.getTradeables()){
          Double currPrice = currReserve.get(t);
          Double newPrice = currPrice;
          if (alloc.containsKey(t)){
            if (alloc.get(t).size() > 1){
              newPrice = newPrice + this.increment;
            }
          } else {
            Logging.log("MixedQueryClockActivity Error: Tradeables don't match up");
          }    
          newReserve.put(t,  newPrice);
        }        
        state.setReserve(newReserve);
      }
    }
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub

  }

}
