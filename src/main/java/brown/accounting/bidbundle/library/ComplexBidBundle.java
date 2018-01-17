package brown.accounting.bidbundle.library;

import java.util.Map;
import java.util.Set;

import brown.accounting.bid.ComplexBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.tradeable.library.Tradeable;

/**
 * A Complex Bid Bundle is a bid bundle that submits bids for vectors 
 * of goods. i.e. (a b c) = 1
 * @author acoggins
 *
 */
public class ComplexBidBundle implements IBidBundle {
  
  private final ComplexBid BIDS;
  private final BundleType BT;
  
  /**
   * For Kryo do not use
   */
  public ComplexBidBundle() {
    this.BIDS = null;
    this.BT = null;
  }
  
  /**
   * Actual constructor
   * @param bid - agent's bid
   * @param agent - agent ID
   */
  public ComplexBidBundle(Map<Set<Tradeable>, Double> bid) {
    this.BIDS = new ComplexBid(bid);
    this.BT = BundleType.Complex;
  }

  @Override
  public double getCost() {
    double max = 0;
    for (Double b : this.BIDS.bids.values()) {
      max = Math.max(b, max);
    }
    return max;
  }

  @Override
  public BundleType getType() {
    return this.BT;
  }
  
  public ComplexBid getBids() {
    return this.BIDS;
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
    return(obj instanceof ComplexBidBundle && 
        ((ComplexBidBundle) obj).BIDS.equals(this.BIDS) &&
        ((ComplexBidBundle) obj).BT.equals(this.BT));
  }

  @Override
  public String toString() {
    return "ComplexBidBundle [BIDS=" + BIDS + "]";
  }
  
}