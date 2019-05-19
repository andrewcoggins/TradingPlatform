package brown.platform.item;

import java.util.List;

public interface ICart {

  public List<IItem> getItems(); 
  
  public IItem getItemByName(String itemName); 
  
  public boolean containsItem(String itemName); 
  
  public void addToCart(IItem item); 
  
  public void removeFromCart(IItem item); 
  
  public void combine(ICart cart); 
  
}
