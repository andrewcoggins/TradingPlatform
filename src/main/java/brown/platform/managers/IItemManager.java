
package brown.platform.managers;

import brown.platform.item.IItem;

/**
 * IItemManager creates and stores items. 
 * @author andrewcoggins
 *
 */
public interface IItemManager {
    
    /**
     * create items for a simulation
     * @param name
     * name of the items to be created
     * @param numItems
     * number of items to be created. 
     */
    void createItems(String name, int numItems); 

    /**
     * get items of a certain name
     * @param name
     * name of the items to get
     * @return
     */
    public IItem getItems(String name);
    
    /**
     * lock the manager. No items can be created after this method is called. 
     */
    void lock();
}
