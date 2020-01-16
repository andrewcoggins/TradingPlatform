package brown.communication.messages;

import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;

public interface ISimulationReportMessage extends IServerToAgentMessage  {

  public Map<Integer, IMarketPublicState> getMarketResults(); 
  
}
