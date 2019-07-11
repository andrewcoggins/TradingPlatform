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

}
