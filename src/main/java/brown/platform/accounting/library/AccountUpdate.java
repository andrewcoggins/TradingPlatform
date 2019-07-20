package brown.platform.accounting.library;

import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.ITransaction;
import brown.platform.item.ICart;

public class AccountUpdate implements IAccountUpdate {

  private Integer TO;
  private ICart CART;
  private Integer FROM;
  private double PRICE;
  private boolean receiveCart; 

  /**
   * For Kryo
   * NO NOT USE
   */
  public AccountUpdate() {
    this.TO = null;
    this.FROM = null;
    this.PRICE = -1;
    this.CART = null;
  }
  
  public AccountUpdate(Integer to, double price, ICart good) {
    this.TO = to; 
    this.FROM = -1; 
    this.PRICE = price; 
    this.CART = good;
    this.receiveCart = false; 
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
  public AccountUpdate(Integer to, Integer from, double price, ICart good) {
    this.TO = to;
    this.FROM = from;
    this.PRICE = price;
    this.CART = good;
  }
  

  @Override
  public Integer getTo() {
    return this.TO; 
  }

  @Override
  public Integer getFrom() {
    return this.FROM; 
  }

  @Override
  public Double getCost() {
    return this.PRICE; 
  }

  @Override
  public ICart getCart() {
    return CART;
  }
  
  @Override
  public boolean receiveCart() {
    return this.receiveCart;
  }
  
  
  @Override
  public ITransaction toTransaction() {
    return new Transaction(TO, FROM, PRICE, CART); 
  }

  @Override
  public String toString() {
    return "AccountUpdate [TO=" + TO + ", CART=" + CART + ", FROM=" + FROM
        + ", PRICE=" + PRICE + ", receiveCart=" + receiveCart + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((CART == null) ? 0 : CART.hashCode());
    result = prime * result + ((FROM == null) ? 0 : FROM.hashCode());
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((TO == null) ? 0 : TO.hashCode());
    result = prime * result + (receiveCart ? 1231 : 1237);
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
    if (CART == null) {
      if (other.CART != null)
        return false;
    } else if (!CART.equals(other.CART))
      return false;
    if (FROM == null) {
      if (other.FROM != null)
        return false;
    } else if (!FROM.equals(other.FROM))
      return false;
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    if (TO == null) {
      if (other.TO != null)
        return false;
    } else if (!TO.equals(other.TO))
      return false;
    if (receiveCart != other.receiveCart)
      return false;
    return true;
  }
  
}
