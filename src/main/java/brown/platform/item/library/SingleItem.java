package brown.platform.item.library;

import brown.platform.item.ISingleItem;
import brown.platform.tradeable.ITradeable;

public class SingleItem extends AbsItem implements ISingleItem {

  public SingleItem(ITradeable tradeable) {
    super(tradeable, 1); 
  }

}
