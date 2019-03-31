package brown.platform.item.library;

import brown.platform.item.IItem;
import brown.platform.tradeable.ITradeable;

public abstract class AbsItem implements IItem {

  private int count; 
  private ITradeable tradeable; 
  
  public AbsItem(ITradeable tradeable, int count) {
    this.tradeable = tradeable; 
    this.count = count; 
  }

  @Override
  public ITradeable getItem() {
    return this.tradeable;
  }

  @Override
  public int getItemCount() {
    return this.count;
  }
  
  @Override
  public String toString() {
    return "AbsItem [count=" + count + ", tradeable=" + tradeable + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + count;
    result = prime * result + ((tradeable == null) ? 0 : tradeable.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbsItem other = (AbsItem) obj;
    if (count != other.count)
      return false;
    if (tradeable == null) {
      if (other.tradeable != null)
        return false;
    } else if (!tradeable.equals(other.tradeable))
      return false;
    return true;
  }

}
