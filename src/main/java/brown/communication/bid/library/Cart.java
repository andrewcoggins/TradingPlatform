package brown.communication.bid.library;

import java.util.List;

import brown.communication.bid.ICart;
import brown.communication.bid.IItem;

public class Cart extends AbsCart implements ICart {

  public Cart(List<IItem> items) {
    super(items); 
  }
  
}
