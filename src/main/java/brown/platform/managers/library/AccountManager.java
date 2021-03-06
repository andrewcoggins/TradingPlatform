package brown.platform.managers.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import brown.auction.endowment.IEndowment;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.library.AccountInitializationMessage;
import brown.communication.messages.library.BankUpdateMessage;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.accounting.IAccount;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.Account;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.managers.IAccountManager;

/**
 * Account manager stores and manages accounts for the server.
 * 
 * @author acoggins, modified by kerry
 */
public class AccountManager implements IAccountManager {

  private Map<Integer, IAccount> accounts;
  private boolean lock;

  /**
   * AccountManager constructor stores Hashmap, and is initially unlocked. 
   */
  public AccountManager() {
    this.accounts = new ConcurrentHashMap<Integer, IAccount>();
    this.lock = false;
  }

  public void createAccount(Integer agentID, IEndowment endowment) {
    if (!this.lock) {
      synchronized (agentID) {
        this.accounts.put(agentID,
            new Account(agentID, endowment.getMoney(), endowment.getCart()));
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

  public void reendow(Integer agentID, IEndowment initialEndowment) {
    try {
      IAccount endowAccount = this.accounts.get(agentID);
      endowAccount.clear();
      initialEndowment.getCart().getItems()
          .forEach(item -> endowAccount.addItems(item));
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
    Map<Integer, IBankUpdateMessage> bankUpdates =
        new HashMap<Integer, IBankUpdateMessage>();
    for (Integer agentID : this.accounts.keySet()) {
      IAccount agentAccount = this.accounts.get(agentID);
      ICart agentTradeables = agentAccount.getAllItems();
      List<IItem> items = new LinkedList<IItem>();
      agentTradeables.getItems().forEach(item -> items.add(item));
      IBankUpdateMessage agentBankUpdate = new AccountInitializationMessage(0,
          agentID, new Cart(items), agentAccount.getMoney());
      bankUpdates.put(agentID, agentBankUpdate);
    }
    return bankUpdates;
  }

  @Override
  public Map<Integer, IBankUpdateMessage>
      constructBankUpdateMessages(List<IAccountUpdate> accountUpdates) {
    Map<Integer, IBankUpdateMessage> bankUpdates =
        new HashMap<Integer, IBankUpdateMessage>();

    accountUpdates.forEach(update -> bankUpdates.put(update.getTo(),
        new BankUpdateMessage(0, update.getTo(),
            update.receiveCart() ? update.getCart()
                : new Cart(new LinkedList<IItem>()),
            update.receiveCart() ? new Cart(new LinkedList<IItem>())
                : update.getCart(),
            update.receiveCart() ? -1.0 * update.getCost()
                : update.getCost())));
    return bankUpdates;
    
    
  }

  @Override
  public void updateAccounts(List<IAccountUpdate> accountUpdates) {
    for (IAccountUpdate update : accountUpdates) {
      if (this.accounts.containsKey(update.getTo())) {
        IAccount account = this.accounts.get(update.getTo()); 
        if (update.receiveCart()) {
          account.removeMoney(update.getCost());
          update.getCart().getItems().forEach(item -> account.addItems(item));
        } else {
          account.addMoney(update.getCost());
          update.getCart().getItems().forEach(item -> account.removeItems(item));
        }
      }
    }

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
