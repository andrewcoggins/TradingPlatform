package brown.accounting;

import java.util.List;

import brown.accounting.library.Account;

/**
 * account manager stores agent accounts.
 * @author andrew
 *
 */
public interface IAccountManager {

  /**
   * Get all accounts from the manager, in a List
   */
  public List<Account> getAccounts();
  
  /**
   * @param ID - agent's ID whose account is to be stored
   * @param account - agent's account
   */
  public void setAccount(Integer ID, Account account);
  
  /**
   * gets an account from an agent's private id, if it exists
   * @param ID - agent's private id
   * @return - account, if it exists; otherwise null
   */
  public Account getAccount(Integer ID);
  
  /**
   * does the manager contain an account associated with an agent's ID? 
   * @param ID - agent's private ID
   * @return - Boolean indicating if account manager has this account
   */
  public Boolean containsAcct(Integer ID);
}