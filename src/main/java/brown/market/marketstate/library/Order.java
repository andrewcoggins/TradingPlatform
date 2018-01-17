package brown.market.marketstate.library; 

import brown.accounting.Transaction;
import brown.tradeable.ITradeable;

/**
* An order is ...
*/
public class Order {
  
	public final Integer TO;
	public final ITradeable GOOD;
	public final Integer FROM;
	public final double PRICE;
	public double QUANTITY;
	
	/**
	 * For Kryo do not use
	 */
	public Order() {
		this.TO = null;
		this.FROM = null;
		this.PRICE = -1;
		this.QUANTITY = -1;
		this.GOOD = null;
	}
	
	/**
	 * Actual order constructor
	 * @param to
	 * @param from
	 * @param price
	 * @param quantity
	 * @param good
	 */
	public Order(Integer to, Integer from, double price, double quantity, ITradeable good) {
		this.TO = to;
		this.FROM = from;
		this.PRICE = price;
		this.QUANTITY = quantity;
		this.GOOD = good;
	}
	
	public void updateQuantity(double quantity) {
		this.QUANTITY = quantity;
	}

	// ??? this is all about Transactions !! 
	public Transaction toTransaction() {
		return new Transaction(this.TO, this.FROM, this.PRICE, this.QUANTITY, this.GOOD);
	}

	public Order updatePrice(double price) {
		return new Order(this.TO, this.FROM, price, this.QUANTITY, this.GOOD);
	}

	public Order updateToAgent(Integer newAgent) {
		return new Order(newAgent, this.FROM, this.PRICE, this.QUANTITY, this.GOOD);
	}
	
	public Order updateFromAgent(Integer newAgent) {
   return new Order(this.TO, newAgent, this.PRICE, this.QUANTITY, this.GOOD);
  }
	  
	@Override
	public String toString() {
		return "<" + this.TO + "," + this.GOOD + "," + this.PRICE + ">";
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
    result = prime * result + ((GOOD == null) ? 0 : GOOD.hashCode());
    temp = Double.doubleToLongBits(QUANTITY);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    Order other = (Order) obj;
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    if (FROM == null) {
      if (other.FROM != null)
        return false;
    } else if (!FROM.equals(other.FROM))
      return false;
    if (GOOD == null) {
      if (other.GOOD != null)
        return false;
    } else if (!GOOD.equals(other.GOOD))
      return false;
    if (Double.doubleToLongBits(QUANTITY) != Double
        .doubleToLongBits(other.QUANTITY))
      return false;
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    return true;
  }
	
}
