package brown.communication.bid.library;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.util.Arrays;

import brown.communication.bid.IBidBundle;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.user.agent.library.localbid.IBidVector;

/**
 * A map from Tradeables to a Bids.
 * @author andrew
 *
 */
public class GVSM9BidBundle extends PerItemBidBundle implements IBidBundle {
	private int agentNum;

	public GVSM9BidBundle() {
		super(); 
		this.agentNum = 0;
	}

	public GVSM9BidBundle(IBidVector bids, int agentNum) {
		super(bids); 
		this.agentNum = agentNum;
	}

	public int agentNum() {
		return this.agentNum;
	}

}