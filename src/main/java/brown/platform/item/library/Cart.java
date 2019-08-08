package brown.platform.item.library;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

/**
 * Cart class
 * @author andrewcoggins
 *
 */
public class Cart extends AbsCart implements ICart {
  
  // for kryo
  public Cart() {
    super(); 
  }
  
  /**
   * Cart takes in a list of IItem
   * @param items
   */
  public Cart(List<IItem> items) {
    super(items); 
  }
  
}
