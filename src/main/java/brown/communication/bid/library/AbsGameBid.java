package brown.communication.bid.library;

import brown.communication.bid.IGameBid;

public abstract class AbsGameBid extends AbsBid implements IGameBid {
  
  private Integer action; 
  
  public AbsGameBid(Integer action) {
    super(BidType.GameBid); 
    this.action = action; 
  }
  
  public Integer getAction() {
    return this.action; 
  }
  
  @Override
  public String toString() {
    return "GameBid [action=" + action + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((action == null) ? 0 : action.hashCode());
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
    AbsGameBid other = (AbsGameBid) obj;
    if (action == null) {
      if (other.action != null)
        return false;
    } else if (!action.equals(other.action))
      return false;
    return true;
  }

}