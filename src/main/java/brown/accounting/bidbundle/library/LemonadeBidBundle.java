package brown.accounting.bidbundle.library;

import brown.accounting.bid.LemonadeBid;
import brown.accounting.bidbundle.IBidBundle;

public class LemonadeBidBundle implements IBidBundle {

  private Integer numberBid; 
  private BundleType bType; 
  
  /**
   * For Kryo do not use
   */
  public LemonadeBidBundle() {
    this.numberBid = null; 
    this.bType = null;  
  }
  
  public LemonadeBidBundle(Integer numberBid) {
    this.numberBid = numberBid;
    this.bType = BundleType.Lemonade;
  }
  
  @Override
  public double getCost() {
    // TODO Auto-generated method stub
    return this.numberBid;
  }

  @Override
  public LemonadeBid getBids() {
    // TODO Auto-generated method stub
    return new LemonadeBid(this.numberBid);
  }

  @Override
  public IBidBundle wipeAgent(Integer ID) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BundleType getType() {
    // TODO Auto-generated method stub
    return this.bType;
  }
  
}
