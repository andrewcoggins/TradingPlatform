package brown.rules.irpolicies.library; 

import java.util.List;

import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.bidbundle.library.GameBidBundle;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.LemonadeReportMessage;
import brown.messages.library.TradeMessage;
import brown.rules.irpolicies.IInformationRevelationPolicy;

public class LemonadeAnonymous implements IInformationRevelationPolicy{

  @Override
  public void handleInfo() {
    // TODO Auto-generated method stub    
  }

  @Override
  public void setReport(ICompleteState state) {
    Integer[] report = new Integer[12];    

    List<TradeMessage> bids = state.getBids();
    for (TradeMessage b:bids){
      if (b.Bundle.getType() != BundleType.Lemonade)
        continue;
      GameBidBundle lemonadeBid = (GameBidBundle) b.Bundle;
      int index = lemonadeBid.getBids().move;
      report[index] = report[index] + 1;
    }
    
    state.setReport(new LemonadeReportMessage(report));
  }  
}