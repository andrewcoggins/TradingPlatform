package brown.rules.library; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.logging.Logging;
import brown.market.library.BlankStateInfo;
import brown.market.library.PredictionMarketInfo;
import brown.market.library.PrevStateInfo;
import brown.market.library.PrevStateType;
import brown.market.marketstate.IMarketState;
import brown.messages.library.GameReportMessage;
import brown.messages.library.PredictionMarketReport;
import brown.rules.IInformationRevelationPolicy;

public class PredictionMarketInformation implements IInformationRevelationPolicy{
  
  public PredictionMarketInformation() {
  }

  @Override
  public void handleInfo() {
  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,GameReportMessage> reports = new HashMap<Integer,GameReportMessage>();        
    PrevStateInfo pstate = state.getPrevState();    
    if (pstate.getType() != PrevStateType.PREDICTION){
      Logging.log("WRONG PREV STATE INFO CONFIG");
    } else {
      PredictionMarketInfo info = (PredictionMarketInfo) pstate;
      for (List<Integer> group : state.getGroups()){
        for (Integer agent : group){      
          reports.put(agent, new PredictionMarketReport(info.trueCoin));        
        }
      }                     
    }    
    state.setReport(reports);
  }

  @Override
  public void reset() {
  }

  @Override
  public void constructSummaryState(IMarketState state) {  
    state.setSummaryState(new BlankStateInfo());
  }  
}