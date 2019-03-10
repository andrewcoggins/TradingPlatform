package brown.user.agent;

import brown.communication.messages.IBankUpdateMessage;
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

  /**
   * Sent whenever an agent gets a game report
   * @param gamereport -- contains a report of recent activity
   */


}
