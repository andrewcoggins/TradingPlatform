package brown.platform.item.library;

import brown.platform.item.IItem;

public class Item extends AbsItem implements IItem {
  
  public Item() {
    super(); 
  }
  
  public Item(String name, int count) {
    super(name, count); 
  }
  
  public Item(String name) {
    super(name, 1); 
  }
  
}
