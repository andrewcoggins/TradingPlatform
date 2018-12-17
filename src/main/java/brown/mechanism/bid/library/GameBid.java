package brown.mechanism.bid.library;

import brown.mechanism.bid.IBid;

/**
 * A bid that is used in games like the lemonade game.
 * @author acoggins
 *
 */
public class GameBid  extends AbsGameBid implements IBid {
	
	public GameBid(Integer action) {
		super(action);
	}

}