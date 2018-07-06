package brown.user.agent;

import brown.platform.messages.library.AccountResetMessage;
import brown.platform.messages.library.BankUpdateMessage;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.PrivateInformationMessage;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back.
 * @author andrew
 *
 */
public interface IAgent { 

  /**
   * Sent whenever an agent's bank account changes
   * @param bankUpdate - contains the old and new bank accounts
   */
  public void onBankUpdate(BankUpdateMessage bankUpdate);

  /**
   * Sent whenever you get a game report (at the end of each outer round)
   * @param gamereport
   */
  public void onGameReport(GameReportMessage gameReport);

  /**
   * gives agent any private information they they may need.
   * @param privateInfo
   */
  public void onPrivateInformation(PrivateInformationMessage privateInfo);
  
  /**
   * Sent whenever an agent's account is reset
   * @param acctReset - contains acct initialization info
   */
  public void onAccountResetMessage(AccountResetMessage accountResetMessage);    
}

  
 