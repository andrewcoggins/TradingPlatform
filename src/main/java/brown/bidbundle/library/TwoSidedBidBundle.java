package brown.bidbundle.library;

import brown.bid.library.TwoSidedBid;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;

/**
 * the bid bundle for non-auction games is called GameBidBundle
 * @author acoggins
 *
 */
public class TwoSidedBidBundle implements IBidBundle {

  private final TwoSidedBid BID;
  private final BundleType BT;  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public TwoSidedBidBundle() {
    this.BID = null;
    this.BT = null;
  }
  
  public TwoSidedBidBundle(TwoSidedBid bid) {
    this.BID = bid;
    this.BT = BundleType.TWOSIDED;
  }
  
  @Override
  public TwoSidedBid getBids() {
    return this.BID;
  }

  @Override
  public BundleType getType() {
    return this.BT;
  } 
  
  @Override
  public double getCost() {
    return 0.;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((BID == null) ? 0 : BID.hashCode());
    result = prime * result + ((BT == null) ? 0 : BT.hashCode());
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
    TwoSidedBidBundle other = (TwoSidedBidBundle) obj;
    if (BID == null) {
      if (other.BID != null)
        return false;
    } else if (!BID.equals(other.BID))
      return false;
    if (BT != other.BT)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "TwoSidedBidBundle [BID=" + BID + ", BT=" + BT + "]";
  }
}
