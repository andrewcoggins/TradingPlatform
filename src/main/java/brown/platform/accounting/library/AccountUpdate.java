package brown.platform.accounting.library;

import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.ITransaction;
import brown.platform.item.IItem;

public class AccountUpdate implements IAccountUpdate {

  public final Integer TO;
  public final IItem ITEM;
  public final Integer FROM;
  public final double PRICE;

  /**
   * For Kryo
   * NO NOT USE
   */
  public AccountUpdate() {
    this.TO = null;
    this.FROM = null;
    this.PRICE = -1;
    this.ITEM = null;
  }
  
  public AccountUpdate(Integer to, double price, IItem good) {
    this.TO = to; 
    this.FROM = -1; 
    this.PRICE = price; 
    this.ITEM = good;
  }
  
  /**
   * Constructor for an accountUpdate needs a ToAgent, FromAgent, price, quantity, 
   * GoodName, and good
   * @param to
   * agent whose account is to be updated
   * @param from
   * agent who the udpate is from (not important in oneSided)
   * @param price
   * money gained or lost
   * @param quantity
   * quantity of goods gained or lost. 
   * @param goodName
   * name of the good added or lost. 
   * @param good
   * tradeable to be added or removed. 
   */
  public AccountUpdate(Integer to, Integer from, double price, IItem good) {
    this.TO = to;
    this.FROM = from;
    this.PRICE = price;
    this.ITEM = good;
  }

  @Override
  public ITransaction toTransaction() {
    return new Transaction(TO, FROM, PRICE, ITEM); 
  }

  @Override
  public String toString() {
    return "AccountUpdate [TO=" + TO + ", ITEM=" + ITEM + ", FROM=" + FROM
        + ", PRICE=" + PRICE + "]";
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
    AccountUpdate other = (AccountUpdate) obj;
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
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    return true;
  }
  
}
