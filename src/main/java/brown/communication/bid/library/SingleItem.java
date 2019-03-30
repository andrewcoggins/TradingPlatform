package brown.communication.bid.library;

import brown.communication.bid.ISingleItem;
import brown.platform.tradeable.ITradeable;

public class SingleItem extends AbsItem implements ISingleItem {

  public SingleItem(ITradeable tradeable) {
    super(tradeable, 1); 
  }

}
