package brown.communication.messages.library;

import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.ISimulationReportMessage;
import brown.user.agent.IAgent;

public class SimulationReportMessage extends AbsServerToAgentMessage implements ISimulationReportMessage {

  private Map<Integer, IMarketPublicState> marketResults; 
  
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

}
