package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.PrevStateType;
import brown.auction.prevstate.library.BlankStateInfo;
import brown.auction.prevstate.library.PredictionMarketInfo;
import brown.auction.prevstate.library.PrevStateInfo;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.logging.library.Logging;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.PredictionMarketReport;

public class PredictionMarketInformation implements IInformationRevelationPolicy{
  
  public PredictionMarketInformation() {
  }

  @Override
  public void setReport(IMarketState state) {
    Map<Integer,List<GameReportMessage>> reports = new HashMap<Integer,List<GameReportMessage>>();        
    PrevStateInfo pstate = state.getPrevState();    
    if (pstate.getType() != PrevStateType.PREDICTION){
      Logging.log("WRONG PREV STATE INFO CONFIG");
    } else {
      PredictionMarketInfo info = (PredictionMarketInfo) pstate;
      for (List<Integer> group : state.getGroups()){
        for (Integer agent : group){     
          List<GameReportMessage> reportList = new LinkedList<GameReportMessage>();
          reportList.add(new PredictionMarketReport(info.trueCoin));
          reports.put(agent,reportList);        
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