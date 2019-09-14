package brown.platform.accounting.library;

import java.util.LinkedList;

import brown.platform.accounting.IAccount;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class Account implements IAccount {

  private Integer ID;
  private double money;
  private ICart goods;

  /**
   * For Kryo DO NOT USE
   */
  public Account() {
    this.ID = null;
    this.money = 0.0;
    this.goods = null;
  }

  /**
   * An account is constructed with an agent's private ID, starting money, and
   * starting items
   * 
   * @param ID agent's private ID
   * @param money agent's starting money
   * @param goods agent's starting goods.
   */
  public Account(Integer ID, double money, ICart goods) {
    this.ID = ID;
    this.money = money;
    this.goods = goods;
  }

  public int getID() {
    return this.ID;
  }

  public double getMoney() {
    return this.money;
  }

  public IItem getItems(String name) {
    return this.goods.getItemByName(name);
  }

  public ICart getAllItems() {
    return this.goods;
  }

  public void addItems(IItem itemToAdd) {
    this.goods.addToCart(itemToAdd);
  }

  public void removeItems(IItem itemToRemove) {
    this.goods.removeFromCart(itemToRemove);
  }

  public void addMoney(double money) {
    this.money += money;
  }

  public void removeMoney(double newMoney) {
    this.money -= newMoney;
  }

  public void clear() {
    this.money = 0.0;
    this.goods = new Cart(new LinkedList<IItem>());
  }

  @Override
  public String toString() {
    return "Account [ID=" + ID + ", money=" + money + ", goods=" + goods + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((goods == null) ? 0 : goods.hashCode());
    long temp;
    temp = Double.doubleToLongBits(money);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    Account other = (Account) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (goods == null) {
      if (other.goods != null)
        return false;
    } else if (!goods.equals(other.goods))
      return false;
    if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
      return false;
    return true;
  }

}
