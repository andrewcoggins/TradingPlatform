package brown.agent;

import brown.messages.library.ErrorMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.MarketUpdateMessage;
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
   * Gets called when the server sends a registration method back to the agent (2nd part of handshake)
   * @param registration - includes the agent's new ID
   */
  public void onRegistration(RegistrationMessage registration);
 
  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(BankUpdateMessage bankUpdate);

  /**
   * Sent whenever you get a market update (within an outer round of a game)
   * @param marketUpdate
   */
  public void onMarketUpdate(MarketUpdateMessage marketUpdate);
  
  /**
   * Sent whenever you get a game report (at the end of each outer round)
   * @param gamereport
   */
  public void onGameReport(GameReportMessage gameReport);

  /**
   * Whenever a message is rejected, a rejection message is sent
   * @param message - includes a string about rejection
   */
  public void onErrorMessage(ErrorMessage message);
  
  /**
   * gives agent any private information they they may need.
   * @param privateInfo
   */
  public void onPrivateInformation(PrivateInformationMessage privateInfo);
  
}

  
 