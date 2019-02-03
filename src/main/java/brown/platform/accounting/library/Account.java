package brown.platform.accounting.library; 

import java.util.List;
import java.util.Map;

import brown.logging.library.ErrorLogging;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.accounting.IAccount;

/**
 * an account belongs to an agent and stores money and goods for that agent
 * 
 */
public class Account implements IAccount {
  
	private Integer ID;
	private double money;
	private Map<String, List<ITradeable>> tradeables;
	
	/**
	 * For Kryo
	 * DO NOT USE
	 */
	public Account() {
		this.ID = null;
		this.money = 0.0;
		this.tradeables = null;
	}
	
	/**
	 * Constructor with agent ID, initial balance, and goods
	 * @param ID - agent ID
	 * @param monies - initial monies
	 * @param goods - initial goods
	 */
	public Account(Integer ID, double monies, Map<String, List<ITradeable>> goods) {
		this.ID = ID;
		this.money = monies;
		this.tradeables = goods;
	}
	
	public int getID() {
    return this.ID;
  }
	
	public double getMoney() {
	  return this.money;
	}
	
	public List<ITradeable> getGoods(String name) {
	  return this.tradeables.get(name);
	}
	
	public Map<String, List<ITradeable>> getAllGoods() {
	  return this.tradeables; 
	}
	
	public void addTradeables(String name, List<ITradeable> tradeables) {
	  if (this.tradeables.keySet().contains(name)) {
	    List<ITradeable> tList = this.tradeables.get(name); 
	    tList.addAll(tradeables);
	    this.tradeables.put(name, tList); 
	  } else {
	    this.tradeables.put(name, tradeables); 
	  }
	}

	public void removeTradeables(String name, List<ITradeable> tradeables) {
	  if (this.tradeables.keySet().contains(name)) {
	    List<ITradeable> existing = this.tradeables.get(name);
	    existing.removeAll(tradeables); 
	    this.tradeables.put(name, existing); 
	  } else {
	    ErrorLogging.log("ERROR: Account " + this.ID.toString() + ": tradeable name " + name +  "not in account so can't be removed." );
	  }
	}
	
	public void addMoney(double money) {
	    this.money += money; 
	}
	 
  public void removeMoney(double newMoney) {
    this.money -= newMoney;
  }

	public void clear() {
	  this.money = 0.0;
	  this.tradeables.clear(); 
	}

  @Override
  public String toString() {
    return "Account [ID=" + ID + ", money=" + money + ", tradeables="
        + tradeables + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    long temp;
    temp = Double.doubleToLongBits(money);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result =
        prime * result + ((tradeables == null) ? 0 : tradeables.hashCode());
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
    Account other = (Account) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
      return false;
    if (tradeables == null) {
      if (other.tradeables != null)
        return false;
    } else if (!tradeables.equals(other.tradeables))
      return false;
    return true;
  }

}
