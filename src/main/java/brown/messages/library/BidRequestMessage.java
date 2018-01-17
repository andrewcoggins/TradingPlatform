package brown.messages.library;

import java.util.Set;

import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.agent.AbsAgent;
import brown.tradeable.library.Tradeable;

/**
 * An Agent sends a TradeRequest as a Message to bid on auctions.
 * 
 * @author lcamery
 */


// RENAME: TradeRequestMessage


public class BidRequestMessage extends AbsMessage {
  public final Integer AuctionID;
  public final Set<Tradeable> Goods;
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
  public BidRequestMessage(int ID, Integer auctionID, BundleType type, IBidBundle bundle, Set<Tradeable> goods) {
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
