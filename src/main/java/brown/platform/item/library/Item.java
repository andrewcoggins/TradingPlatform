package brown.platform.item.library;

import brown.platform.item.IItem;

/**
 * IItem class
 * @author andrewcoggins
 *
 */
public class Item extends AbsItem implements IItem {
  
  // for kryo
  public Item() {
    super(); 
  }
  
  /**
   * Item can take a name and a count
   * @param name
   * name of the item
   * @param count
   * count contained within the item. 
   */
  public Item(String name, int count) {
    super(name, count); 
  }
  
  /**
   * Item can take a name. 
   * @param name
   * name of the item
   */
  public Item(String name) {
    super(name, 1); 
  }
  
}
