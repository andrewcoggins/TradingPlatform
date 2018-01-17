package brown.accounting; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Account manager stores accounts for use in the server.
 * @author acoggins
 *
 */
public class AccountManager {
  
	private Map<Integer, Account> accounts;

	/**
	 * manager that stores accounts for use by the server
	 */
	public AccountManager() {
		this.accounts = new ConcurrentHashMap<Integer, Account>();
	}
	
	/**
	 * put an account in the account manager
	 * @param ID - agent's ID whose account is to be put in the manager
	 * @param account - agent's account
	 */
	public void setAccount(Integer ID, Account account) {
		synchronized (ID) {
			accounts.put(ID, account);
		}
	}
	
	//PRIVATE??
	/**
	 * gets an account from an Agent's private id
	 * @param ID - agent's private id
	 * @return - account
	 */
	public Account getAccount(Integer ID) {
	  //check for existence first? why not necessary?
	  //if accounts.containsKey(ID) ...
		return accounts.get(ID);
	}
	
	/**
	 * get all accounts in the manager, in a List
	 */
	public List<Account> getAccounts() {
		return new ArrayList<Account>(accounts.values());
	}
	
	/**
	 * does the manager contain an account associated with the input ID? 
	 * @param ID - agent's private ID
	 * @return whether or not the account exists
	 */
	public Boolean containsAcct(Integer ID) {
		return accounts.containsKey(ID);
	}

}