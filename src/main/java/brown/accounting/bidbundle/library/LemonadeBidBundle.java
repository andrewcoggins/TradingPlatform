package brown.accounting.bidbundle.library;

import brown.accounting.bid.LemonadeBid;
import brown.accounting.bidbundle.IBidBundle;

public class LemonadeBidBundle implements IBidBundle {

  private Integer BIDS;
  private BundleType BT; 
  
  /**
   * For Kryo do not use
   */
  public LemonadeBidBundle() {
    this.BIDS = null; 
    this.BT = null;  
  }
  
  public LemonadeBidBundle(Integer numberBid) {
    this.BIDS = numberBid;
    this.BT = BundleType.Lemonade;
  }
  
  @Override
  public double getCost() {
    return this.BIDS;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((BT == null) ? 0 : BT.hashCode());
    result = prime * result + ((BIDS == null) ? 0 : BIDS.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return(obj instanceof LemonadeBidBundle && 
        ((LemonadeBidBundle) obj).BIDS.equals(this.BIDS) &&
        ((LemonadeBidBundle) obj).BIDS.equals(this.BT));
  }

  @Override
  public LemonadeBid getBids() {
    return new LemonadeBid(this.BIDS);
  }

  @Override
  public BundleType getType() {
    return this.BT;
  }  
}
