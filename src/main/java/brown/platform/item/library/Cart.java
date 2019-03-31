package brown.platform.item.library;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class Cart extends AbsCart implements ICart {

  public Cart(List<IItem> items) {
    super(items); 
  }
  
}
