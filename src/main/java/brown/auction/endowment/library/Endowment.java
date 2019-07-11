package brown.auction.endowment.library;

import brown.auction.endowment.IEndowment;
import brown.platform.item.ICart;

public class Endowment implements IEndowment {
  
  private ICart cart; 
  private Double money; 
  
  public Endowment(ICart aCart, Double money) {
    this.cart = aCart; 
    this.money = money; 
  }
  
  public ICart getCart() {
    return this.cart; 
  }

  
  public Double getMoney() {
    return this.money; 
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cart == null) ? 0 : cart.hashCode());
    result = prime * result + ((money == null) ? 0 : money.hashCode());
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
    Endowment other = (Endowment) obj;
    if (cart == null) {
      if (other.cart != null)
        return false;
    } else if (!cart.equals(other.cart))
      return false;
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Endowment [cart=" + cart + ", money=" + money + "]";
  }
  

}
