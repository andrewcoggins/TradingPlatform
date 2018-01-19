package brown.agent;

import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;

public interface IAgent { 

  /**
   * Agents send a message to the server to register
   * @param registration - includes the agent's new ID
   */
  public void onRegistration(RegistrationMessage registration);
 
  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(BankUpdateMessage bankUpdate);

  // FIX ME !!
  /**
   * Sent whenever you get a report
   * @param marketUpdate
   */
  public void onMarketUpdate(GameReportMessage marketUpdate);

  // and provides information about the current market state as a part of the request
  // REALLY ??
  /**
   * When an auction is running, the server requests bids using this method
   * @param tradeRequest - auction metadata
   */
  public void onTradeRequest(BidRequestMessage tradeRequest);

  //includes the rejected message and might say why??
  /**
   * Whenever a message is rejected, a rejection message is sent
   * 
   * @param message - includes the rejected message and might say why
   */
  public void onAck(AckMessage message);
  
  // TODO: Create a NegotiateChannel
  // Move to AbsNegotiateAgent
  /**
   * Whenever another agent requests a trade, either directly with this agent
   * or to all agents, this method is invoked with the details of the request.
   * 
   * @param negotiateRequest
   *            - from fields describe what this agent will get
   *            - to fields describe what it is requested to give
   */
  public void onNegotiateRequest(NegotiateRequestMessage tradeRequest);
  
}

  
 