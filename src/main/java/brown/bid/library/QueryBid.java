package brown.bid.library;

import java.util.List;

import brown.bid.IBid;
import brown.tradeable.library.ComplexTradeable;

/** A bid that submits queries for complex tradeables.
 * @author kerry
 *
 */
public class QueryBid implements IBid {
  public final List<ComplexTradeable> bundles;
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public QueryBid() {
    this.bundles = null;
  }
  
  /**
   * @param bids are represented as a map from ITradeables to doubles
   */
  public QueryBid(List<ComplexTradeable> bundles) {
    this.bundles = bundles;
  }

  @Override
  public String toString() {
    return "QueryBid [bundles=" + bundles + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bundles == null) ? 0 : bundles.hashCode());
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
    QueryBid other = (QueryBid) obj;
    if (bundles == null) {
      if (other.bundles != null)
        return false;
    } else if (!bundles.equals(other.bundles))
      return false;
    return true;
  }
  
  
}
