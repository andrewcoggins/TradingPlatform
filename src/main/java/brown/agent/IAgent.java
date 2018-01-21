package brown.agent;

import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.RegistrationMessage;

/**
 * Agents are responsible for receiving messages from the server and sending bids
 * in the game.
 * @author andrew
 *
 */
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
  
  /**
   * gives agent any private information they they may need.
   * @param privateInfo
   */
  public void onPrivateInformation(PrivateInformationMessage privateInfo);
  
}

  
 