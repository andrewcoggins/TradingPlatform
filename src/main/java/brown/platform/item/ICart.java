package brown.platform.item;

import java.util.List;

/**
 * ICart is a container for Items. 
 * 
 * @author andrewcoggins
 *
 */
public interface ICart {

  /**
   * Get the items in the cart.
   * 
   * @return
   */
  public List<IItem> getItems(); 
  
  /**
   * Get items in the cart by their name.
   * 
   * @param itemName
   * @return
   */
  public IItem getItemByName(String itemName); 
  
  /**
   * Return whether or not the cart contains an item with a specified name.
   * 
   * @param itemName
   * @return
   */
  public boolean containsItem(String itemName); 
  
  /**
   * Add an item to the cart. 
   * 
   * @param item
   */
  public void addToCart(IItem item); 
  
  /**
   * Remove an item from the cart.
   * 
   * @param item
   */
  public void removeFromCart(IItem item); 
  
  /**
   * Combine two carts. 
   * 
   * @param cart
   */
  public void combine(ICart cart); 
  
}
