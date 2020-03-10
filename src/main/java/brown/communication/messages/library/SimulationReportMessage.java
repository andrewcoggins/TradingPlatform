package brown.communication.messages.library;

import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.ISimulationReportMessage;
import brown.user.agent.IAgent;

public class SimulationReportMessage extends AbsServerToAgentMessage implements ISimulationReportMessage {

  private Map<Integer, IMarketPublicState> marketResults; 
  
  /**
   * for kryo do not use
   */
  public SimulationReportMessage() {
    super(null, null); 
    this.marketResults = null; 
  }
  
  public SimulationReportMessage(Integer messageID, Integer agentID, Map<Integer, IMarketPublicState> marketResults) {
    super(messageID, agentID);
    this.marketResults = marketResults; 
  }

  @Override
  public void agentDispatch(IAgent agent) {
    agent.onSimulationReportMessage(this);
  }

  @Override
  public Map<Integer, IMarketPublicState> getMarketResults() {
    return this.marketResults;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((marketResults == null) ? 0 : marketResults.hashCode());
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
    SimulationReportMessage other = (SimulationReportMessage) obj;
    if (marketResults == null) {
      if (other.marketResults != null)
        return false;
    } else if (!marketResults.equals(other.marketResults))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SimulationReportMessage [marketResults=" + marketResults + "]";
  }

}
