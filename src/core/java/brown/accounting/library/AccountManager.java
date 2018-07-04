package brown.accounting.library; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.accounting.IAccountManager;

/**
 * Account manager stores and manages accounts for the server.
 * @author acoggins, modified by kerry
 */
public class AccountManager implements IAccountManager {
  
  // maps agent ID to Account
	private Map<Integer, Account> accounts;

	/**
	 * @param accounts - maps agent IDs to accounts
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
	
  public Account getAccount(Integer ID) {
		return accounts.get(ID);
	}
	
	public Boolean containsAcct(Integer ID) {
		return accounts.containsKey(ID);
	}

  public void reset() {
    this.accounts.clear();
  }

  @Override
  public String toString() {
    return "AccountManager [accounts=" + accounts + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AccountManager other = (AccountManager) obj;
    if (accounts == null) {
      if (other.accounts != null)
        return false;
    } else if (!accounts.equals(other.accounts))
      return false;
    return true;
  }

}