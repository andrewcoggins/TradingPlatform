package brown.platform.accounting;

import brown.platform.accounting.library.Transaction;

/**
 * ITransaction describes a change to an agent account. 
 * @author andrew
 */
public interface ITransaction {
  
  /**
   * @param agentID - Agent ID to keep in transaction (wipes other agent IDs)
   * @return sanitized transaction (Other IDs are set to null)
   */
  public Transaction sanitize(Integer agentID);
  
}