package brown.accounting.library; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Account manager stores and manages accounts for the server.
 * 
 * @author acoggins
 */
public class AccountManager {
  
  // maps agent ID to Account
	private Map<Integer, Account> accounts;

	// why not empty constructor for Kryo?
	
	/**
	 * manager that stores accounts for use by the server
	 * accounts - maps agent ID's to accounts
	 */
	public AccountManager() {
		this.accounts = new ConcurrentHashMap<Integer, Account>();
	}

  /**
   * get all accounts in the manager, in a List
   */
  public List<Account> getAccounts() {
    return new ArrayList<Account>(accounts.values());
  }
  
	/**
	 * @param ID - agent's ID whose account is to be stored
	 * @param account - agent's account
	 */
	public void setAccount(Integer ID, Account account) {
		synchronized (ID) {
			accounts.put(ID, account);
		}
	}
	
	//what happens if an account does not exist?
	/**
	 * gets an account from an agent's private id, if it exists
	 * @param ID - agent's private id
	 * @return - account, if it exists; otherwise, ???
	 */
	public Account getAccount(Integer ID) {
	  //check for existence first? why not necessary?
	  //if accounts.containsKey(ID) ...
		return accounts.get(ID);
	}
	
	//what happens if an account does not exist?
	/**
	 * does the manager contain an account associated with an agent's ID? 
	 * @param ID - agent's private ID
	 * @return an account, if it exists; otherwise, ???
	 */
	public Boolean containsAcct(Integer ID) {
		return accounts.containsKey(ID);
	}

}