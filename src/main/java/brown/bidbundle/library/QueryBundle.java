package brown.bidbundle.library;

import brown.bid.IBid;
import brown.bid.library.CancelBid;
import brown.bid.library.QueryBid;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;

public class QueryBundle implements IBidBundle{
  private final QueryBid BID;
  private final BundleType BT;
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public QueryBundle() {
    this.BID = null;
    this.BT = null;
  }
  
  public QueryBundle(QueryBid bid) {
    this.BID = bid;
    this.BT = BundleType.QUERY;
  }
  
  @Override
  public IBid getBids() {
    return this.BID;
  }

  @Override
  public BundleType getType() {
    return this.BT;
  }

  @Override
  public double getCost() {
    return 0;
  }

}
