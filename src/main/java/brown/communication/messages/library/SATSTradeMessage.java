package brown.communication.messages.library;

import org.spectrumauctions.sats.core.bidlang.xor.XORBid;
import org.spectrumauctions.sats.core.model.Good;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.communication.messageserver.IOnlineMessageServer;
import brown.user.agent.IAgentBackend;

/**
 * Trade message is sent by the agent to the server specifying an action withing
 * a trading environment This may be a bid or some other action.
 * 
 * @author andrew
 *
 */
public class SATSTradeMessage extends TradeMessage implements ITradeMessage {
	private XORBid<Good> xor;
	
	public SATSTradeMessage() {
		super();
		this.xor = null;
	}

	public SATSTradeMessage(Integer messageID, Integer agentID, Integer responseID, Integer auctionID, IBidBundle bid, XORBid<Good> xor) {
		super(messageID, agentID, responseID, auctionID, bid);
		this.xor = xor;
	}

	public SATSTradeMessage(Integer messageID, Integer agentID, Integer auctionID, IBidBundle bid, XORBid<Good> xor) {
		this(messageID, agentID, null, auctionID, bid, xor);
	}
	
	public XORBid<Good> getXOR() {
		return this.xor;
	}

}
