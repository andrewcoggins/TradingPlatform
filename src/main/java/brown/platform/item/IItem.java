package brown.platform.item;

/**
 * The granular Item in the Trading Platform. 
 * 
 * @author andrewcoggins
 *
 */
public interface IItem {

  /**
   * Get the name of the item.
   * 
   * @return
   */
  public String getName(); 
  
  /**
   * Get the count of the item. 
   * 
   * @return
   */
  public int getItemCount(); 
  
  /**
   * Add to the item's count. 
   * 
   * @param numToAdd
   */
  public void addItemCount(int numToAdd); 
  
  /**
   * Remove from the item's count. 
   * 
   * @param numToRemove
   */
  public void removeItemCount(int numToRemove); 
  
}
