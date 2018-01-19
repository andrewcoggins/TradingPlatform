package brown.messages.library;

import java.util.Set;

import brown.agent.AbsAgent;
import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.IBidBundle;
import brown.tradeable.library.MultiTradeable;

/**
 * An Agent sends a TradeRequest as a Message to bid on auctions.
 * 
 * @author lcamery
 */


// RENAME: TradeRequestMessage


public class BidRequestMessage extends AbsMessage {
  public final Integer AuctionID;
  public final Set<MultiTradeable> Goods;
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
  public BidRequestMessage(int ID, Integer auctionID, BundleType type, IBidBundle bundle, Set<MultiTradeable> goods) {
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
