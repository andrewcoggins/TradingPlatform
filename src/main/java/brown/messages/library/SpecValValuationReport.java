package brown.messages.library;

import java.util.Map;

import brown.messages.library.GameReportMessage;
import brown.tradeable.library.ComplexTradeable;

public class SpecValValuationReport extends GameReportMessage {
  private Map<ComplexTradeable,Double> report;
  
  // void kryo
  public SpecValValuationReport(){
    this.report = null;
  }
  
  public SpecValValuationReport(Map<ComplexTradeable,Double> info){
    this.report = info;
  }  

  public GameReportMessage sanitize(Integer agent, Map<Integer,Integer> privateToPublic){    
    return this;
    }

  public Map<ComplexTradeable,Double> getValuation(){
    return(this.report);
  }
  
  @Override
  public String toString() {
    return "SpecValValuationReport [report=" + report + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((report == null) ? 0 : report.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SpecValValuationReport other = (SpecValValuationReport) obj;
    if (report == null) {
      if (other.report != null)
        return false;
    } else if (!report.equals(other.report))
      return false;
    return true;
  }
}
