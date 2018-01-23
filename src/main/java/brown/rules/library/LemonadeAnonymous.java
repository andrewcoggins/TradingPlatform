package brown.rules.library; 

import java.util.List;

import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.library.GameBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;

public class LemonadeAnonymous implements IInformationRevelationPolicy{
  private int numSlots;
  
  public LemonadeAnonymous(int numSlots) {
    this.numSlots = numSlots;
  }

  @Override
  public void handleInfo() {
  }

  @Override
  public void setReport(IMarketState state) {
    Integer[] report = new Integer[numSlots];    
    for (int i = 0; i<numSlots;i++){
      report[i] = 0;
    }        
    
    List<TradeMessage> bids = state.getBids();
    for (TradeMessage b:bids){
      if (b.Bundle.getType() != BundleType.GAME)
        continue;
      GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
      int index = lemonadeBid.getBids().move;
      report[index] = report[index] + 1;
    }
    state.setReport(new LemonadeReportMessage(report));
  }

  @Override
  public void reset() {
  }

  @Override
  public void constructSummaryState(IMarketState state) {    
  }  
}