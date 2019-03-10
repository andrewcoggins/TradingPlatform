package brown.platform.managers;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.IInitialEndowment;

/**
 * account manager stores agent accounts.
 * @author andrew
 *
 */
public interface IAccountManager {

  void createAccount(Integer agentID, IInitialEndowment endowment);

  /**
   * gets an account from an agent's private id, if it exists
   * @param ID - agent's private id
   * @return - account, if it exists;
   *   otherwise null (as per Java maps)
   */
  IAccount getAccount(Integer ID);

  /**
   * Get all accounts from the manager, in a List
   */
  List<IAccount> getAccounts();
  
  /**
   * @param ID - agent's ID whose account is to be stored
   * @param account - agent's account
   */
  void setAccount(Integer ID, IAccount account);

  
  /**
   * does the manager contain an account associated with an agent's ID? 
   * @param ID - agent's private ID
   * @return - Boolean indicating if account manager has this account
   */
  Boolean containsAccount(Integer ID);

  /**
   * ets all accounts to empty state.
   */
  void reset();

  /**
   * resets accounts to their initial endowments, ostensibly as defined in the constructor.
   */
  void reendow(Integer agentID, IInitialEndowment endowment);
  
  
  Map<Integer, IBankUpdateMessage> constructInitializationMessages(); 

  Map<Integer, IBankUpdateMessage> constructBankUpdateMessages(List<IAccountUpdate> accountUpdates); 
  
  void lock();
  
  void updateAccounts(List<IAccountUpdate> accountUpdates); 
  
}