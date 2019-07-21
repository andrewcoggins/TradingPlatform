package brown.platform.item;

/**
 * the granular Item in the Trading Platform. 
 * @author andrewcoggins
 *
 */
public interface IItem {

  /**
   * get the name of the item
   * @return
   */
  public String getName(); 
  
  /**
   * get the count of the item. 
   * @return
   */
  public int getItemCount(); 
  
  /**
   * add to the item's count. 
   * @param numToAdd
   */
  public void addItemCount(int numToAdd); 
  
  /**
   * remove from the item's count. 
   * @param numToRemove
   */
  public void removeItemCount(int numToRemove); 
  
}
