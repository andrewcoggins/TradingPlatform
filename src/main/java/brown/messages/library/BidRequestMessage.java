package brown.messages.library;

import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.bidbundle.IBidBundle;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;
import brown.tradeable.library.Good;

/**
 * An Agent sends a bidRequest as
 * a Message to bid on auctions.
 * 
 * @author lcamery
 */
public class BidRequestMessage extends AbsMessage {
  public final Integer AuctionID;
  public final Set<Good> Goods;
  public final IBidBundle Current;
  public final BundleType TYPE;

  /**
   * Kryo requires empty constructor DO NOT USE
   */
  public BidRequestMessage() {
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
  public BidRequestMessage(int ID, Integer auctionID, BundleType type, IBidBundle bundle, Set<Good> goods) {
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
