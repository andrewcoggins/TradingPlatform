package brown.platform.item;


public interface IItem {

  public String getName(); 
  
  public int getItemCount(); 
  
  public void addItemCount(int numToAdd); 
  
  public void removeItemCount(int numToRemove); 
  
}
