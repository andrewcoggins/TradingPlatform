package brown.accounting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import brown.accounting.library.Account;

public interface IAccountManager {

  /**
   * get all accounts in the manager, in a List
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
   * @return - account, if it exists; otherwise, ???
   */
  public Account getAccount(Integer ID);
  
  /**
   * does the manager contain an account associated with an agent's ID? 
   * @param ID - agent's private ID
   * @return an account, if it exists; otherwise, ???
   */
  public Boolean containsAcct(Integer ID);
}