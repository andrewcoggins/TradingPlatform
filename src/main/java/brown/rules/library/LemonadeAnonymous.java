package brown.rules.library; 

import java.util.List;

import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.GameBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.IInformationRevelationPolicy;

public class LemonadeAnonymous implements IInformationRevelationPolicy{

  @Override
  public void handleInfo() {
  }

  @Override
  public void setReport(ICompleteState state) {
    Integer[] report = new Integer[12];    
    for (int i = 0; i<12;i++){
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
}