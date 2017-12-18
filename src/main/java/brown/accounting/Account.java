package brown.accounting; 

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.tradeable.library.Tradeable;

/**
 * an account belongs to an agent and stores tradeables
 * and money for that agent.
 * @author lcamery
 *
 */
public class Account {
	public final Integer ID;
	private double monies;
	private List<Tradeable> tradeables;
	
	/**
	 * Kryo objects require a blank constructor
	 */
	public Account() {
		this.ID = null;
		this.monies = 0;
		this.tradeables = null;
	}
	
	/**
	 * Account with the owner's ID; no balance or goods
	 * Use this constructor
	 * @param ID : owner ID
	 */
	public Account(Integer ID) {
		this.ID = ID;
		this.monies = new Integer(0);
		this.tradeables = new LinkedList<Tradeable>();
	}
	
	/**
	 * Constructor with starting balance and goods
	 * @param ID : owner's ID
	 * @param monies : starting monies
	 * @param goods : starting goods
	 */
	private Account(Integer ID, double monies, List<Tradeable> goods) {
		this.ID = ID;
		this.monies = monies;
		if (goods != null) {
			this.tradeables = goods;
		}else{
			this.tradeables = new LinkedList<Tradeable>();
		}
	}
	
	public double getMonies() {
	  return this.monies;
	}
	
	public List<Tradeable> getGoods() {
	  return this.tradeables;
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional goods 
	 * @return updated Account
	 */
	public void add(double newMonies, List<Tradeable> newGoods) {
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
	 * @return updated Account
	 */
	//TODO: make this void, along with addall.
	public void add(double newMonies, Tradeable newGood) {
		if (newGood != null) {
	    this.tradeables.add(newGood);
		}
    this.monies += newMonies;
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional good
	 * @return updated Account
	 */
	public void add(double newMonies, Set<Tradeable> newGoods) {
		if (newGoods != null) {
	    List<Tradeable> unique = new LinkedList<Tradeable>();
	    unique.addAll(newGoods);
	    this.tradeables.addAll(unique);
		}
		this.monies += newMonies;
	}
	
	/**
	 * Removes monies and goods; leave 0 or null if gives an already constructed account to a particular agent.not using both
	 * @param newMonies : money to remove
	 * @param newGoods : goods to remove 
	 * @return updated Account
	 */
	public void remove(double removeMonies, List<Tradeable> removeGoods) {
		if (removeGoods != null) {
	    this.tradeables.removeAll(removeGoods);
		}
		this.monies -= removeMonies;
	}

	/**
	 * removes an individual tradeable and money
	 * @param removeMonies
	 * money to be removed
	 * @param t
	 * tradeable to be removed
	 * @return updated account.
	 */
	public void remove(double removeMonies, Tradeable t) {
		if (t != null) {
		  this.tradeables.remove(t); 
		}
    this.monies -= removeMonies;
	}

	/**
	 * add money to an account.
	 * @param add
	 * money to be added
	 * @return
	 * an updated account.
	 */
	public void add(double add) {
		this.monies += add;
	}
	
	/**
	 * copies this account.
	 * @return
	 * copied account.
	 */
	public Account copyAccount() {
		List<Tradeable> forAgent = new LinkedList<Tradeable>();
		for (Tradeable t : this.tradeables) {
			forAgent.add(t);
		}
		
		return new Account(this.ID, this.monies, forAgent);
	}
	
	@Override
	public String toString () {
		return "(" + this.ID + ": " + this.monies + ", " + this.tradeables + ")"; 
	}
}
