package brown.accounting.bidbundle.library;

import brown.accounting.BundleType;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.IBidBundle;

public class Allocation implements IBidBundle {

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((ALLOCATION == null) ? 0 : ALLOCATION.hashCode());
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
    Allocation other = (Allocation) obj;
    if (ALLOCATION == null) {
      if (other.ALLOCATION != null)
        return false;
    } else if (!ALLOCATION.equals(other.ALLOCATION))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Allocation [ALLOCATION=" + ALLOCATION + "]";
  }  
  
}