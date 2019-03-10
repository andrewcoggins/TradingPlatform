package brown.user.agent;

import brown.communication.messages.IBankUpdateMessage;

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
  public void onBankUpdate(IBankUpdateMessage bankUpdate);

  /**
   * Sent whenever an agent gets a game report
   * @param gamereport -- contains a report of recent activity
   */


}
