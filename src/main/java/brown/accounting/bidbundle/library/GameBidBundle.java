package brown.accounting.bidbundle.library;

import brown.accounting.bid.GameBid;
import brown.accounting.bidbundle.IBidBundle;

public class GameBidBundle implements IBidBundle {

  private final GameBid BIDS;
  private final BundleType BT; 
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public GameBidBundle() {
    this.BIDS = null; 
    this.BT = null;  
  }
  
  public GameBidBundle(Integer numberBid) {
    this.BIDS = new GameBid(numberBid);
    this.BT = BundleType.Lemonade;
  }
  
  @Override
  public double getCost() {
    return 0.;
  }
  
  @Override
  public GameBid getBids() {
    return this.BIDS;
  }

  @Override
  public BundleType getType() {
    return this.BT;
  } 
  
  @Override
  public String toString() {
    return "GameBidBundle [BIDS=" + BIDS + ", BT=" + BT + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((BIDS == null) ? 0 : BIDS.hashCode());
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
    GameBidBundle other = (GameBidBundle) obj;
    if (BIDS == null) {
      if (other.BIDS != null)
        return false;
    } else if (!BIDS.equals(other.BIDS))
      return false;
    if (BT != other.BT)
      return false;
    return true;
  }
  
}
