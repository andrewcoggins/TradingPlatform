package brown.communication.messages;

import brown.auction.marketstate.IMarketState;

public interface IInformationMessage extends IServerToAgentMessage {
  
  public IMarketState getPublicState(); 
  
}
