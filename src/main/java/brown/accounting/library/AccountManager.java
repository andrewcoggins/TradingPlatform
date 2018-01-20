package brown.accounting.library; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.accounting.IAccountManager;

/**
 * Account manager stores and manages accounts for the server.
 * 
 * @author acoggins, modified by kerry
 */
public class AccountManager implements IAccountManager {
  
  // maps agent ID to Account
	private Map<Integer, Account> accounts;

	/**
	 * manager that stores accounts for use by the server
	 * accounts - maps agent IDs to accounts
	 */
	public AccountManager() {
		this.accounts = new ConcurrentHashMap<Integer, Account>();
	}

  public List<Account> getAccounts() {
    return new ArrayList<Account>(accounts.values());
  }
  
	public void setAccount(Integer ID, Account account) {
		synchronized (ID) {
			accounts.put(ID, account);
		}
	}
	
// returns null if account does not exist
	public Account getAccount(Integer ID) {
		return accounts.get(ID);
	}
	
	public Boolean containsAcct(Integer ID) {
		return accounts.containsKey(ID);
	}

  public void reset() {
    this.accounts.clear();
  }

}