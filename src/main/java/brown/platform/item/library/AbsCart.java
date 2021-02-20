package brown.platform.item.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.logging.library.ErrorLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

/**
 * Abstract class for a Cart.
 * @author andrewcoggins
 *
 */
public abstract class AbsCart implements ICart {

  private List<IItem> items; 
  private Map<String, IItem> itemMap; 
  
  // for kryo, do not use
  public AbsCart() {
    this.items = new LinkedList<>(); 
    this.itemMap = new HashMap<>(); 
  }
  
  /**
   * Abstract cart takes in a list of IItem
   * @param items
   */
  public AbsCart(List<IItem> items) {
    this.items = items; 
    this.itemMap = new HashMap<String, IItem>(); 
    for(IItem item: items) {
      itemMap.put(item.getName(), item);  
    }
  }
  
  @Override
  public List<IItem> getItems() {
    return this.items;
  }
  
  @Override 
  public IItem getItemByName(String name) {
    return this.itemMap.get(name); 
  }
  
  @Override 
  public boolean containsItem(String name) {
    return this.itemMap.containsKey(name); 
  }
  
  @Override
  public void addToCart(IItem item) {
    if (!this.itemMap.containsKey(item.getName())) {
      this.items.add(item); 
      this.itemMap.put(item.getName(), item); 
    } else {
        IItem itemToChange = this.itemMap.get(item.getName());
        itemToChange.addItemCount(item.getItemCount());
        this.itemMap.put(item.getName(), itemToChange);
    }
  }
  
  @Override
  public void removeFromCart(IItem item) {
    if (this.itemMap.containsKey(item.getName())) {
      for (IItem anItem : this.items) {
        if (anItem.getName().equals(item.getName())) {
          anItem.removeItemCount(item.getItemCount());
    	  this.itemMap.remove(anItem.getName());
    	  this.items.remove(anItem);
          break; 
        }
      } 
    } else {
      ErrorLogging.log("ERROR: could not remove item " + item.toString() + " from cart " + this.toString());
    }
  }
  
  // TODO: 
  // what is this used for? 
  @Override
  public void combine(ICart cart) {


  }
  
  @Override
  public String toString() {
    return "AbsCart [items=" + items + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    AbsCart other = (AbsCart) obj;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

}