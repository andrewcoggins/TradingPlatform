package brown.accounting.library;

import java.util.LinkedList;
import java.util.List;

import brown.tradeable.ITradeable;

//TODO: abstract to the complex case
/**
 * A ledger tracks all trades within a Market. 
 * @author lcamery
 */
public class Ledger {
  
  protected final Integer marketId;
	protected final List<Transaction> transactions;
	protected final List<Transaction> unshared;
	
	/**
	 * For Kryo do not use
	 */
	public Ledger() {
	  this.marketId = null;
		this.transactions = null;
		this.unshared = null;
	}
	
	/**
   * Actual ledger constructor
   * @param marketId
   * 
   * 
   * 
   */
	public Ledger(Integer marketId) {
	  this.marketId = marketId; 
	  this.unshared = new LinkedList<Transaction>();
	  this.transactions = new LinkedList<Transaction>();
	}
	 

	/**
	 * Adds a transaction
	 * @param t - transaction to add
	 */
	public void add(Transaction t) {
		synchronized(transactions) {
			this.transactions.add(t);
			this.unshared.add(t);
		}
	}
	
	public void add(ITradeable good, Integer toID, Double price) {
	  synchronized(transactions) {
	    Transaction trans = new Transaction(toID, null, price, good.getCount(), good);
	    this.transactions.add(trans);
	    this.unshared.add(trans);
	  }
	}
	
	/**
	 * Constructs a set of all transactions
	 * @return set
	 */
	public List<Transaction> getList() {
		return new LinkedList<Transaction>(this.transactions);
	}
	
	/**
	 * Adds a list of transactions
	 * @param trans - list of transactions
	 */
	public void add(List<Transaction> trans) {
		synchronized(transactions) {
			this.transactions.addAll(trans);
			this.unshared.addAll(trans);
		}
	}
	
	/**
	 * Gets the ledger without others' IDs
	 * @param ID - this agent's ID
	 * @return ledger
	 */
	public Ledger getSanitized(Integer ID) {
		Ledger ledger = new Ledger(this.marketId);
		synchronized(transactions) {
			for (Transaction t : this.unshared) {
				ledger.add(t.sanitize(ID));
			}
		}
		return ledger;
	}
	
	/**
	 * Clears the latest set
	 */
	public void clearUnshared() {
		this.unshared.clear();
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((marketId == null) ? 0 : marketId.hashCode());
    result =
        prime * result + ((transactions == null) ? 0 : transactions.hashCode());
    result = prime * result + ((unshared == null) ? 0 : unshared.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return ((obj instanceof Ledger) &&
        ((Ledger)obj).marketId == this.marketId &&
        ((Ledger)obj).transactions == this.transactions &&
        ((Ledger)obj).unshared == this.unshared);
  }

  @Override
  public String toString() {
    return "Ledger [marketId=" + marketId + ", transactions=" + transactions
        + ", unshared=" + unshared + "]";
  }


}
