package brown.platform.item;

import java.util.List;

public interface ICart {

  public List<IItem> getItems(); 
  
  public IItem getItemByName(String itemName); 
  
  public boolean containsItem(String itemName); 
  
}
