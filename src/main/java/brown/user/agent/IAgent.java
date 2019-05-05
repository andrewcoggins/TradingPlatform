package brown.user.agent;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.system.client.IClient;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back.
 * @author andrew
 */
public interface IAgent extends IClient { 

  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(IBankUpdateMessage bankUpdate);

  public void onInformationMessage(IInformationMessage informationMessage); 
  
  public void onTradeRequestMessage(ITradeRequestMessage tRequestMessage); 
  
  public void onValuationMessage(IValuationMessage valuationMessage); 

}
