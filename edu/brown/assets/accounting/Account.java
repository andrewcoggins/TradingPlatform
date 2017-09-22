package brown.assets.accounting;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.tradeables.Asset;

/**
 * an account belongs to an agent and stores tradeables
 * and money for that agent.
 * @author acoggins
 *
 */
public class Account {
	public final Integer ID;
	public final double monies;
	public final List<Asset> tradeables;
	
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
		this.tradeables = new LinkedList<Asset>();
	}
	
	/**
	 * Constructor with starting balance and goods
	 * @param ID : owner's ID
	 * @param monies : starting monies
	 * @param goods : starting goods
	 */
	private Account(Integer ID, double monies, List<Asset> goods) {
		this.ID = ID;
		this.monies = monies;
		if (goods != null) {
			this.tradeables = goods;
		}else{
			this.tradeables = new LinkedList<Asset>();
		}
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional goods 
	 * @return updated Account
	 */
	public Account addAll(double newMonies, List<Asset> newGoods) {
		if (newGoods == null) {
			return new Account(this.ID, newMonies+this.monies, tradeables);
		}
		
		this.tradeables.addAll(newGoods);
		return new Account(this.ID, newMonies+this.monies, tradeables);
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional good
	 * @return updated Account
	 */
	public Account add(double newMonies, Asset newGood) {
		if (newGood == null) {
			return new Account(this.ID, newMonies+this.monies, tradeables);
		}
		this.tradeables.add(newGood);
		return new Account(this.ID, newMonies+this.monies, tradeables);
	}
	
	/**
	 * Adds monies and goods; leave 0 or null if not using both
	 * @param newMonies : additional money
	 * @param newGoods : additional good
	 * @return updated Account
	 */
	public Account add(double newMonies, Set<Asset> newGoods) {
		if (newGoods == null) {
			return new Account(this.ID, newMonies+this.monies, tradeables);
		}
		Set<Asset> goods = new HashSet<Asset>();
		goods.addAll(this.tradeables);
		goods.addAll(newGoods);
		List<Asset> unique = new LinkedList<Asset>();
		unique.addAll(goods);
		return new Account(this.ID, newMonies+this.monies, unique);
	}
	
	/**
	 * Removes monies and goods; leave 0 or null if gives an already constructed account to a particular agent.not using both
	 * @param newMonies : money to remove
	 * @param newGoods : goods to remove 
	 * @return updated Account
	 */
	public Account remove(double removeMonies, List<Asset> removeGoods) {
		if (removeGoods == null) {
			return new Account(this.ID, this.monies-removeMonies, this.tradeables);
		}
		
		this.tradeables.removeAll(removeGoods);
		return new Account(this.ID, this.monies-removeMonies, this.tradeables);
	}

	/**
	 * removes an individual tradeable and money
	 * @param removeMonies
	 * money to be removed
	 * @param t
	 * tradeable to be removed
	 * @return updated account.
	 */
	public Account remove(double removeMonies, Asset t) {
		if (t == null) {
			return new Account(this.ID, this.monies-removeMonies, this.tradeables);
		}
		
		List<Asset> unique = new LinkedList<Asset>();
		for (Asset o : this.tradeables) {
			if (!o.equals(t)) {
				unique.add(o);
			}
		}
		return new Account(this.ID, this.monies-removeMonies, unique);
	}

	/**
	 * add money to an account.
	 * @param add
	 * money to be added
	 * @return
	 * an updated account.
	 */
	public Account add(double add) {
		return new Account(this.ID, this.monies+add, this.tradeables);
	}
	
	/**
	 * copies this account.
	 * @return
	 * copied account.
	 */
	public Account toAgent() {
		List<Asset> forAgent = new LinkedList<Asset>();
		for (Asset t : this.tradeables) {
			forAgent.add(t.toAgent(this.ID));
		}
		
		return new Account(this.ID, this.monies, forAgent);
	}
	
	@Override
	public String toString () {
		return "(" + this.ID + ": " + this.monies + ", " + this.tradeables + ")"; 
	}
}
