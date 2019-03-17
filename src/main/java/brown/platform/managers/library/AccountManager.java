package brown.platform.managers.library; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.library.AccountInitializationMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.IInitialEndowment;
import brown.platform.accounting.library.Account;
import brown.platform.managers.IAccountManager;

/**
 * Account manager stores and manages accounts for the server.
 * @author acoggins, modified by kerry
 */
public class AccountManager implements IAccountManager {

	private Map<Integer, IAccount> accounts;
	private boolean lock;

	//TODO: put endowment manager in account manager. 
	
	public AccountManager() {
		this.accounts = new ConcurrentHashMap<>();
		this.lock = false;
	}

	public void createAccount(Integer agentID, IInitialEndowment endowment) {
	    if (!this.lock) {
            synchronized (agentID) {
                this.accounts.put(agentID, new Account(agentID, endowment.getMoney(), endowment.getGoods()));
            }
        } else {
            PlatformLogging.log("Creation denied: account manager locked.");
        }
    }

    public IAccount getAccount(Integer ID) {
        return accounts.get(ID);
    }

  public List<IAccount> getAccounts() {
    return new ArrayList<>(accounts.values());
  }
  
	public void setAccount(Integer ID, IAccount account) {
		synchronized (ID) {
            if (this.accounts.containsKey(ID)) {
                accounts.put(ID, account);
            }
		}
	}

  public void reset() {
    for (Integer agentID : this.accounts.keySet()) {
      IAccount agentAccount = this.accounts.get(agentID); 
      agentAccount.clear();
      this.accounts.put(agentID, agentAccount); 
    }
  }

  public Boolean containsAccount(Integer ID) {
      return accounts.containsKey(ID);
  }  

  public void reendow(Integer agentID, IInitialEndowment initialEndowment) {
      try {
          IAccount endowAccount = this.accounts.get(agentID);
          endowAccount.clear(); 
          for (String s : initialEndowment.getGoods().keySet()) {
            endowAccount.addTradeables(s, initialEndowment.getGoods().get(s));
          }
          endowAccount.addMoney(initialEndowment.getMoney());
          this.accounts.put(agentID, endowAccount); 
      } catch (NullPointerException n) {
        ErrorLogging.log("ERROR: AccountManager: ID not found.");
        throw n; 
      }
  }

  public void lock() {
    this.lock = true;
  }
  
  @Override
  public Map<Integer, IBankUpdateMessage> constructInitializationMessages() {
    Map<Integer, IBankUpdateMessage> bankUpdates = new HashMap<Integer, IBankUpdateMessage>(); 
    for (Integer agentID : this.accounts.keySet()) {
      IAccount agentAccount = this.accounts.get(agentID); 
      IBankUpdateMessage agentBankUpdate = new AccountInitializationMessage(0, agentID, agentAccount.getAllGoods(), agentAccount.getMoney());
      bankUpdates.put(agentID, agentBankUpdate); 
    }
    return bankUpdates;
  }
  
  @Override
  public Map<Integer, IBankUpdateMessage>
      constructBankUpdateMessages(List<IAccountUpdate> accountUpdates) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateAccounts(List<IAccountUpdate> accountUpdates) {
    // TODO Auto-generated method stub
    
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