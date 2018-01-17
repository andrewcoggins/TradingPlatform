package brown.accounting; 

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.tradeable.ITradeable;

/**
 * an account belongs to an agent and stores money and goods for that agent
 * 
 * @author lcamery
 * @editor amy
 */
public class Account {
  
	public final Integer ID;
	private double monies;
	private List<ITradeable> tradeables;
	
	/**
	 * Kryo objects require a blank constructor
	 */
	public Account() {
		this.ID = null;
		this.monies = 0.0;
		this.tradeables = null;
	}
	
	/**
	 * Constructor with only agent ID; no money; no goods
	 * @param ID - agent ID
	 */
	public Account(Integer ID) {
		this.ID = ID;
		this.monies = 0.0;
		this.tradeables = new LinkedList<ITradeable>();
	}
	
	/**
   * Constructor with only agent ID and initial balance; no goods
   * @param ID - agent ID
   * @param monies - initial monies
   */
  public Account(Integer ID, double monies) {
    this.ID = ID;
    this.monies = monies;
    this.tradeables = new LinkedList<ITradeable>();
  }
	
	/**
	 * Constructor with agent ID, initial balance, and goods
	 * @param ID - agent ID
	 * @param monies - initial monies
	 * @param goods - initial goods
	 */
	public Account(Integer ID, double monies, List<ITradeable> goods) {
		this.ID = ID;
		this.monies = monies;
		if (goods != null) {
			this.tradeables = goods;
		} else {
			this.tradeables = new LinkedList<ITradeable>();
		}
	}
	
	//ADDED: is this okay, or should this info not be accessible?
	public double getID() {
    return this.ID;
  }
	
	public double getMonies() {
	  return this.monies;
	}
	
	public List<ITradeable> getGoods() {
	  return this.tradeables;
	}
	
	/**
   * Add money to an account
   * @param newMonies - money to be added
   * @return updated account
   */
  public void add(double newMonies) {
    this.monies += newMonies;
  }
  
  private void addHelper(double newMonies, List<ITradeable> newGoods) {
    if (newGoods == null) {
      throw new NullPointerException("Cannot add null tradeables");
    }
    this.tradeables.addAll(newGoods);
    this.monies += newMonies;
  }
  
  /**
   * @param newMonies : add money
   * @param newGoods : add goods 
   * @return updated account
   */
  //AMY; should never deliberately call with null
  public void AMYadd(double newMonies, List<ITradeable> newGoods) {
    addHelper(newMonies, newGoods);
  }
  
  public void AMYadd(double newMonies, Set<ITradeable> newGoods) {
    List<ITradeable> unique = new LinkedList<ITradeable>();
    unique.addAll(newGoods);
    addHelper(newMonies, unique);
  }
  
  public void AMYadd(double newMonies, ITradeable newGood) {
    List<ITradeable> oneGood = new LinkedList<ITradeable>();
    oneGood.add(newGood);
    addHelper(newMonies, oneGood);
  }
  
  // CAN WE DELETE ALL OF THIS ??
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional goods 
	 * @return updated account
	 */
	public void add(double newMonies, List<ITradeable> newGoods) {
		if (newGoods == null) {
			this.monies += newMonies;
			return;
		}
		this.tradeables.addAll(newGoods);
		this.monies += newMonies; 
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional good
	 * @return updated account
	 */
	public void add(double newMonies, ITradeable newGood) {
		if (newGood != null) {
	    this.tradeables.add(newGood);
		}
    this.monies += newMonies;
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional good
	 * @return updated account
	 */
	public void add(double newMonies, Set<ITradeable> newGoods) {
		if (newGoods != null) {
	    List<ITradeable> unique = new LinkedList<ITradeable>();
	    unique.addAll(newGoods);
	    this.tradeables.addAll(unique);
		}
		this.monies += newMonies;
	}
	// END DELETE
	
	 /**
   * Remove money from an account
   * @param newMonies - money to be removed
   * @return updated account
   */
  public void remove(double newMonies) {
    this.monies -= newMonies;
  }
  
  private void removeHelper(double newMonies, List<ITradeable> newGoods) {
    if (newGoods == null) {
      throw new NullPointerException("Cannot add null tradeables");
    }
    this.tradeables.removeAll(newGoods);
    this.monies -= newMonies;
  }
	
  //CREATE PARALLEL STRUCTURE TO ABOVE
	/**
	 * Removes monies and goods; leave 0 or null if gives an already constructed account to a particular agent.not using both
	 * @param removeMonies - money to remove
	 * @param removeGoods - goods to remove 
	 * @return updated account
	 */
	public void remove(double removeMonies, List<ITradeable> removeGoods) {
		if (removeGoods != null) {
	    this.tradeables.removeAll(removeGoods);
		}
		this.monies -= removeMonies;
	}

	/**
	 * Removes an individual good and money
	 * @param removeMonies - money to be removed
	 * @param good - to be removed
	 * @return updated account
	 */
	public void remove(double removeMonies, ITradeable removeGood) {
		if (removeGood != null) {
		  if (!this.tradeables.contains(removeGood)) {
		    return;
		  }
		  this.tradeables.remove(removeGood); 
		}
    this.monies -= removeMonies;
	}

	/** clears an account
	 */
	public void clear() {
	  this.monies = 0.0;
	  this.tradeables = new LinkedList<ITradeable>();
	}
	
	/**
	 * copies an account
	 * @return copied account
	 */
	public Account copyAccount() {
		List<ITradeable> copyTradeables = new LinkedList<ITradeable>();
		for (ITradeable t : this.tradeables) {
			copyTradeables.add(t);
		}
		
		return new Account(this.ID, this.monies, copyTradeables);
	}
	
	@Override
	public String toString () {
		return "(" + this.ID + ": " + this.monies + ", " + this.tradeables + ")"; 
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    long temp;
    temp = Double.doubleToLongBits(monies);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((tradeables == null) ? 0 : tradeables.hashCode());
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
    if (Double.doubleToLongBits(monies) != Double.doubleToLongBits(other.monies))
      return false;
    if (tradeables == null) {
      if (other.tradeables != null)
        return false;
    } else if (!tradeables.equals(other.tradeables))
      return false;
    return true;
  }
	
}
