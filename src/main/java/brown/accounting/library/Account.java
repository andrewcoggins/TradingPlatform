package brown.accounting.library; 

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.accounting.IAccount;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;

/**
 * an account belongs to an agent and stores money and goods for that agent
 * 
 * @author lcamery
 * @editor amy
 */
public class Account implements IAccount {
  
	public final Integer ID;
	private double monies;
	private List<ITradeable> tradeables;
	
	/**
	 * For Kryo
	 * DO NOT USE
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
		this.tradeables = goods;
	}
	
	public double getID() {
    return this.ID;
  }
	
	public double getMonies() {
	  return this.monies;
	}
	
	public List<ITradeable> getGoods() {
	  return this.tradeables;
	}
	
  public void add(double newMonies) {
    this.monies += newMonies;
  }
  
  public void add(double newMonies, List<ITradeable> newGoods) {
    this.tradeables.addAll(newGoods);
    this.monies += newMonies;    
  }
  
  public void add(double newMonies, Set<ITradeable> newGoods) {
    this.tradeables.addAll(newGoods);
    this.monies += newMonies;
  }
  
  public void add(double newMonies, ITradeable newGood) {    
    this.tradeables.add(newGood);
    this.monies += newMonies;
  }
	
  public void remove(double newMonies) {
    this.monies -= newMonies;
  }
  
  /**
   * Flattens collections of ITradeables to lists of SimpleTradeables
   * @param goods - Collection of ITradeables
   * @return "Flattened" collection - the goods represented as a list of SimpleTradeables
   */
  private List<SimpleTradeable> flattenHelper(Collection<ITradeable> goods) {
    List<SimpleTradeable>toReturn = new LinkedList<SimpleTradeable>();    
    for (ITradeable good : goods){
      toReturn.addAll(good.flatten());
    }
    return toReturn;
  }
	
	public void remove(double removeMonies, List<ITradeable> removeGoods) {
	  this.monies -= removeMonies;

	  // Need to flatten goods so they can be compared
	  List<SimpleTradeable> myGoods = flattenHelper(this.tradeables);	  
    List<SimpleTradeable> toRemove = flattenHelper(removeGoods);

    myGoods.removeAll(toRemove);    
    this.tradeables = new LinkedList<ITradeable>(myGoods);
	}
	
	 public void remove(double removeMonies, Set<ITradeable> removeGoods) {
	    this.monies -= removeMonies;

	    List<SimpleTradeable> myGoods = flattenHelper(this.tradeables);   
	    List<SimpleTradeable> toRemove = flattenHelper(removeGoods);

	    myGoods.removeAll(toRemove);    
	    this.tradeables = new LinkedList<ITradeable>(myGoods);
	  }

	public void remove(double removeMonies, ITradeable removeGood) {
	   this.monies -= removeMonies;

	  List<SimpleTradeable> myGoods = flattenHelper(this.tradeables);   
	  
	  myGoods.remove(removeGood);
    this.tradeables = new LinkedList<ITradeable>(myGoods);    
	}

	public void clear() {
	  this.monies = 0.0;
	  this.tradeables = new LinkedList<ITradeable>();
	}
	
	public Account copyAccount() {
		List<ITradeable> copyTradeables = new LinkedList<ITradeable>();
		for (ITradeable t : this.tradeables) {
			copyTradeables.add(t);
		}		
		return new Account(this.ID, this.monies, copyTradeables);
	}

  @Override
  public String toString() {
    return "Account [ID=" + ID + ", monies=" + monies + ", tradeables="
        + tradeables + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    long temp;
    temp = Double.doubleToLongBits(monies);
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
    if (Double.doubleToLongBits(monies) != Double
        .doubleToLongBits(other.monies))
      return false;
    if (tradeables == null) {
      if (other.tradeables != null)
        return false;
    } else if (!tradeables.equals(other.tradeables))
      return false;
    return true;
  }

}
