
package brown.platform.managers;

import brown.platform.item.IItem;

public interface IItemManager {


    void createItems(String name, int numItems); 

    public IItem getItems(String name);

    
    void lock();
}
