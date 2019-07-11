package brown.communication.messages;

import brown.auction.value.valuation.IGeneralValuation;

public interface IValuationMessage extends IServerToAgentMessage {

  public IGeneralValuation getValuation(); 
}
