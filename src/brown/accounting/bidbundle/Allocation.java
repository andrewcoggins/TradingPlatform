package brown.accounting.bidbundle;

import brown.accounting.BundleType;
import brown.accounting.bid.SimpleBid;

public class Allocation extends AbsBidBundle {

  private final SimpleBid ALLOCATION;
  
  public Allocation(SimpleBid bids) {
    if (bids == null) {
      throw new IllegalArgumentException("Null bids");
    }
    this.ALLOCATION = bids;
  }
  
  @Override
  public double getCost() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public IBidBundle wipeAgent(Integer ID) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BundleType getType() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SimpleBid getBids() {
    return this.ALLOCATION;
  }
  
}