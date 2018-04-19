package brown.messages.library;

import java.util.Map;

import brown.tradeable.ITradeable;

public class CombinatorialClockReport  extends GameReportMessage {
  public Map<ITradeable, Double> winnings;
  
  public CombinatorialClockReport(){
    this.winnings = null;
  }
  
  public CombinatorialClockReport(Map<ITradeable, Double> winnings){
    this.winnings = winnings;
  }  

  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    return this;
  }

}
