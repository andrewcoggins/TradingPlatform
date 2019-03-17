package brown.communication.bid.library;

import brown.communication.bid.ICart;

public class Cart extends AbsCart implements ICart {

  public Cart (String tradeableName, Integer tradeableNum) {
    super(tradeableName, tradeableNum); 
  }
  
}
