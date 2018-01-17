package brown.accounting;

import brown.tradeable.ITradeable;

/**
 * A transaction is a trade that transpired.
 * Each one is recorded in the ledger.
 */
public class Transaction {
  
	public final Integer TO;
	public final ITradeable TRADEABLE;
	public final Integer FROM;
	public final double PRICE;
	public final double QUANTITY;
	public final long TIMESTAMP;
	
	/**
	 * For Kryo do not use
	 */
	public Transaction() {
		this.TO = null;
		this.FROM = null;
		this.PRICE = -1;
		this.QUANTITY = -1;
		this.TIMESTAMP = 0;
		this.TRADEABLE = null;
	}
	
	/**
	 * Actual transaction constructor
	 * @param to
	 * @param from
	 * @param price
	 * @param quantity
	 * @param good
	 */
	public Transaction(Integer to, Integer from, double price, double quantity, ITradeable good) {
		this.TO = to;
		this.FROM = from;
		this.PRICE = price;
		this.QUANTITY = quantity;
		this.TRADEABLE = good;
		this.TIMESTAMP = System.currentTimeMillis();
	}

	public Transaction sanitize(Integer ID) {
		return new Transaction(ID != null && ID.equals(TO) ? TO : null,
				ID != null && ID.equals(FROM) ? FROM : null,
				PRICE, QUANTITY, TRADEABLE);
	}

  @Override
  public String toString() {
    return "Transaction [TO=" + TO + ", TRADEABLE=" + TRADEABLE + ", FROM="
        + FROM + ", PRICE=" + PRICE + ", QUANTITY=" + QUANTITY + ", TIMESTAMP="
        + TIMESTAMP + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(QUANTITY);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (int) (TIMESTAMP ^ (TIMESTAMP >>> 32));
    result = prime * result + ((TO == null) ? 0 : TO.hashCode());
    result = prime * result + ((TRADEABLE == null) ? 0 : TRADEABLE.hashCode());
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
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    if (Double.doubleToLongBits(QUANTITY) != Double
        .doubleToLongBits(other.QUANTITY))
      return false;
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    if (TRADEABLE == null) {
      if (other.TRADEABLE != null)
        return false;
    } else if (!TRADEABLE.equals(other.TRADEABLE))
      return false;
    return true;
  }
	
}
