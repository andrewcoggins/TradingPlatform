package brown.platform.item;

import java.util.List;

/**
 * ICart is a container for Items. 
 * @author andrewcoggins
 *
 */
public interface ICart {

  /**
   * get the items inside the ICart
   * @return
   */
  public List<IItem> getItems(); 
  
  /**
   * get an item in the ICart by its name
   * @param itemName
   * @return
   */
  public IItem getItemByName(String itemName); 
  
  /**
   * return whether or not the ICart contains an item with 
   * a specified name
   * @param itemName
   * @return
   */
  public boolean containsItem(String itemName); 
  
  /**
   * add an item to the cart. 
   * @param item
   */
  public void addToCart(IItem item); 
  
  /**
   * remove an item from the cart
   * @param item
   */
  public void removeFromCart(IItem item); 
  
  /**
   * combine two carts. 
   * @param cart
   */
  public void combine(ICart cart); 
  
}
