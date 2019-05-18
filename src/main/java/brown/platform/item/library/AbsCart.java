package brown.platform.item.library;

import java.util.List;
import java.util.Map;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

public abstract class AbsCart implements ICart {

  private List<IItem> items; 
  private Map<String, IItem> itemMap; 
  
  public AbsCart(List<IItem> items) {
    this.items = items; 
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
