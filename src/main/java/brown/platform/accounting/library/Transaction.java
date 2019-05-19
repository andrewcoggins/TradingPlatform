package brown.platform.accounting.library;

import brown.platform.accounting.ITransaction;
import brown.platform.item.IItem;

/**
 * A transaction is a trade that transpired.
 * Each one is recorded in the ledger.
 */
public class Transaction implements ITransaction {
  
	public final Integer TO;
	public final Integer FROM;
	public final double PRICE;
	public final IItem ITEM; 
	public final long TIMESTAMP;
	
	/**
	 * For Kryo 
	 * DO NOT USE
	 */
	public Transaction() {
		this.TO = null;
		this.FROM = null;
		this.PRICE = -1;
		this.TIMESTAMP = 0;
		this.ITEM = null;
	}
	
	/**
	 * Actual constructor
	 * @param to
	 * @param from
	 * @param price
	 * @param quantity
	 * @param good
	 */
	public Transaction(Integer to, Integer from, double price, IItem good) {
		this.TO = to;
		this.FROM = from;
		this.PRICE = price;
		this.ITEM = good;
		this.TIMESTAMP = System.currentTimeMillis();
	}

	public Transaction sanitize(Integer ID) {
		return new Transaction(ID != null && ID.equals(TO) ? TO : null,
				ID != null && ID.equals(FROM) ? FROM : null,
				PRICE, ITEM);
	}
	
  @Override
  public String toString() {
    return "Transaction [TO=" + TO + ", FROM=" + FROM + ", PRICE=" + PRICE
        + ", ITEM=" + ITEM + ", TIMESTAMP=" + TIMESTAMP + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
    result = prime * result + ((ITEM == null) ? 0 : ITEM.hashCode());
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (TIMESTAMP ^ (TIMESTAMP >>> 32));
    result = prime * result + ((TO == null) ? 0 : TO.hashCode());
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
    Transaction other = (Transaction) obj;
    if (FROM == null) {
      if (other.FROM != null)
        return false;
    } else if (!FROM.equals(other.FROM))
      return false;
    if (ITEM == null) {
      if (other.ITEM != null)
        return false;
    } else if (!ITEM.equals(other.ITEM))
      return false;
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    if (TIMESTAMP != other.TIMESTAMP)
      return false;
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    return true;
  }

	
}
