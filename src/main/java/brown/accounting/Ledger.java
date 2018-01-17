package brown.accounting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.Allocation;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.tradeable.ITradeable;
import brown.tradeable.library.Tradeable;

//TODO: abstract to the complex case
/**
 * A ledger tracks all trades within a Market. 
 * @author lcamery
 *
 */
public class Ledger {
  protected final Integer marketId;
	protected final List<Transaction> transactions;
	protected final Map<ITradeable, Transaction> latest;
	protected final List<Transaction> unshared;
	
	/**
	 * For Kryo do not use
	 */
	public Ledger() {
	  this.marketId = null;
		this.transactions = null;
		this.latest = null;
		this.unshared = null;
	}
	

	 public Ledger(Integer marketId) {
	    this.marketId = marketId; 
	    this.unshared = new LinkedList<Transaction>();
	    this.transactions = new LinkedList<Transaction>();
	    this.latest = new HashMap<ITradeable, Transaction>();
	  }
	 
	 /**
	  * for convenience of implementation in the market class.
	  * @param marketId
	  * @param initialAlloc
	  */
	 public Ledger(Integer marketId, Allocation initialAlloc) {
     this.marketId = marketId; 
     this.unshared = new LinkedList<Transaction>();
     this.transactions = new LinkedList<Transaction>();
     this.latest = new HashMap<ITradeable, Transaction>();
     for (Tradeable t : initialAlloc.getBids().bids.keySet()) {
       this.add(t, initialAlloc.getBids().bids.get(t));
     }
   }

	/**
	 * Adds a transaction
	 * @param t : transaction to add
	 */
	public void add(Transaction t) {
		synchronized(transactions) {
			this.latest.put(t.TRADEABLE,t);
			this.transactions.add(t);
			this.unshared.add(t);
		}
	}
	
	public void add(ITradeable tradeable, MarketState mState) {
	  synchronized(transactions) {
	    Transaction tr = new Transaction(mState.AGENTID, null, mState.PRICE, tradeable.getCount(), tradeable);
	    this.latest.put(tradeable, tr);
	    this.transactions.add(tr);
	    this.unshared.add(tr);
	  }
	}
	
//	public void addAll(IBidBundle bids) {
//	  synchronized(transactions) {
//	  if (bids != null) {
//	    // how to fix this, how to fix this.
//	    // could add individual transactions instead.
//	    if (bids.getType() == BundleType.Simple) {
//	      SimpleBidBundle castedBids = (SimpleBidBundle) bids;
//	      for (Entry<Tradeable, MarketState> t : castedBids.getBids().bids.entrySet()) { 
//	        Transaction tr = new Transaction(t.getValue().AGENTID, null, t.getValue().PRICE, 1, t.getKey());
//	        this.latest.put(t.getKey(), tr);
//	        this.transactions.add(tr); 
//	        this.unshared.add(tr);
//	      }
//	    } else if (bids.getType() == BundleType.Complex) {
//	      
//	    }
//	  }
//	 }
//	}
	
	/**
	 * Constructs a set of all transactions
	 * @return set
	 */
	public List<Transaction> getList() {
		return new LinkedList<Transaction>(this.transactions);
	}
	
	/**
	 * Gets the latest transactions
	 * @return
	 */
	public Set<Transaction> getLatest() {
		return new HashSet<Transaction>(this.latest.values());
	}

	/**
	 * Adds a list of transactions
	 * @param trans
	 */
	public void add(List<Transaction> trans) {
		synchronized(transactions) {
			for (Transaction t : trans) {
				this.latest.put(t.TRADEABLE, t);
			}
			this.transactions.addAll(trans);
			this.unshared.addAll(trans);
		}
	}
	
	/**
	 * Gets the ledger without others' IDs
	 * @param ID : this agent's ID
	 * @return ledger
	 */
	public Ledger getSanitized(Integer ID) {
		Ledger ledger = new Ledger(null);
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
	public void clearLatest() {
		this.unshared.clear();
	}


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((latest == null) ? 0 : latest.hashCode());
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
    if (latest == null) {
      if (other.latest != null)
        return false;
    } else if (!latest.equals(other.latest))
      return false;
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
