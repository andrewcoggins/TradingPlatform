package brown.communication.bid.library;

import brown.communication.bid.IItem;
import brown.platform.tradeable.ITradeable;

public class MultiItem extends AbsItem implements IItem {

  public MultiItem(ITradeable tradeable, int count) {
    super(tradeable, count); 
  }
}
