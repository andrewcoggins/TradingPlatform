package brown.user.agent;

import brown.mechanism.messages.library.AccountResetMessage;
import brown.mechanism.messages.library.BankUpdateMessage;
import brown.mechanism.messages.library.GameReportMessage;
import brown.mechanism.messages.library.PrivateInformationMessage;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back.
 * @author andrew
 */
public interface IAgent { 

  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(BankUpdateMessage bankUpdate);

  /**
   * Sent whenever an agent gets a game report
   * @param gamereport -- contains a report of recent activity
   */
  public void onGameReport(GameReportMessage gameReport);

  /**
   * Sends an agent their private information
   * @param privateInfo -- agent's particular characteristics
   */
  public void onPrivateInformation(PrivateInformationMessage privateInfo);
  
  /**
   * Sent whenever an agent's account is reset
   * @param acctReset - contains acct initialization info
   */
  public void onAccountInitialization(AccountResetMessage accountResetMessage);

}
