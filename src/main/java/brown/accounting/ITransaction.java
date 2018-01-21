package brown.accounting;

import brown.accounting.library.Transaction;

/**
 * transaction details processed changes in tradeables and monies in 
 * agent accounts.
 * @author andrew
 *
 */
public interface ITransaction {
  
  /**
   * @param agentID - Agent ID to keep in transaction (wipes other agent IDs)
   * @return sanitized transaction (Other IDs are set to null)
   */
  public Transaction sanitize(Integer agentID);
  
}