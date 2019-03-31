package brown.platform.item.library;

import brown.platform.item.IItem;
import brown.platform.tradeable.ITradeable;

public class MultiItem extends AbsItem implements IItem {

  public MultiItem(ITradeable tradeable, int count) {
    super(tradeable, count); 
  }
  
}
