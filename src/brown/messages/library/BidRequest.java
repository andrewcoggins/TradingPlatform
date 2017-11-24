package brown.messages.library;

import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.bidbundle.IBidBundle;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;
import brown.tradeable.library.Tradeable;

/**
 * An Agent sends a bidRequest as
 * a Message to bid on auctions.
 * 
 * @author lcamery
 */
public class BidRequest extends AbsMessage {
  public final Integer AuctionID;
  public final Set<Tradeable> Goods;
  public final IBidBundle Current;
  public final BundleType TYPE;

  /**
   * Kryo requires empty constructor DO NOT USE
   */
  public BidRequest() {
    super(null);
    this.AuctionID = null;
    this.Current = null;
    this.Goods = null;
    this.TYPE = null;
  }

  /**
   * Constructor.
   *  
   * @param ID
   * @param auctionID
   * @param bundleType
   * @param bundle
   * @param goods
   */
  public BidRequest(int ID, Integer auctionID, BundleType type, IBidBundle bundle, Set<Tradeable> goods) {
    super(ID);
    this.AuctionID = auctionID;
    this.Current = bundle;
    this.Goods = goods;
    this.TYPE = type;
  }

@Override
public void dispatch(AbsAgent agent) {
	agent.onTradeRequest(this); 
}

}
