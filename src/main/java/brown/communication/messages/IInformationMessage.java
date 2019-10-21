package brown.communication.messages;

import brown.auction.marketstate.IMarketPublicState;

public interface IInformationMessage extends IServerToAgentMessage {
  
  public IMarketPublicState getPublicState(); 
  
}
