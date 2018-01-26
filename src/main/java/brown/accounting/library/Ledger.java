package brown.accounting.library;

import java.util.LinkedList;
import java.util.List;

import brown.accounting.ILedger;
import brown.tradeable.ITradeable;

/**
 * A ledger tracks all trades within a Market. 
 * @author lcamery
 * @editor kerry
 * 
 */
public class Ledger implements ILedger{
  
  protected final Integer marketId;
	protected final List<Transaction> transactions;
	protected final List<Transaction> unshared;
	
	/**
	 * For Kryo 
	 * DO NOT USE
	 */
	public Ledger() {
	  this.marketId = null;
		this.transactions = null;
		this.unshared = null;
	}
	
	public Ledger(Integer marketId) {
	  this.marketId = marketId; 
	  this.unshared = new LinkedList<Transaction>();
	  this.transactions = new LinkedList<Transaction>();
	}	 

  @Override
	public void add(Transaction t) {
		synchronized(transactions) {
			this.transactions.add(t);
			this.unshared.add(t);
		}
	}
	
  @Override
	 public void add(List<Transaction> trans) {
	    synchronized(transactions) {
	      this.transactions.addAll(trans);
	      this.unshared.addAll(trans);
	    }
	  }

	@Override
	public void add(ITradeable good, Integer toID, Double price) {
	  synchronized(transactions) {
	    Transaction trans = new Transaction(toID, null, price, good.getCount(), good);
	    this.transactions.add(trans);
	    this.unshared.add(trans);
	  }
	}
	
	@Override
	public List<Transaction> getList() {
	  return this.transactions;
	}
		
  @Override
  public List<Transaction> getUnshared() {
    return this.unshared;
  }

  @Override
	public Ledger getSanitizedUnshared(Integer ID) {
		Ledger ledger = new Ledger(this.marketId);
		synchronized(transactions) {
			for (Transaction t : this.unshared) {
				ledger.add(t.sanitize(ID));
			}
		}
		return ledger;
	}
	
	public void clearUnshared() {
		this.unshared.clear();
	}

  @Override
  public String toString() {
    return "Ledger [marketId=" + marketId + ", transactions=" + transactions
        + ", unshared=" + unshared + "]";
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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ledger other = (Ledger) obj;
    if (marketId == null) {
      if (other.marketId != null)
        return false;
    } else if (!marketId.equals(other.marketId))
      return false;
    if (transactions == null) {
      if (other.transactions != null)
        return false;
    } else if (!transactions.equals(other.transactions))
      return false;
    if (unshared == null) {
      if (other.unshared != null)
        return false;
    } else if (!unshared.equals(other.unshared))
      return false;
    return true;
  }

}
