package brown.messages.library;

import brown.agent.AbsAgent;

/**
 * message provided to agent when there is a server error 
 * caused by a message an agent sent
 * @author andrew
 */
public class AckMessage extends AbsMessage {
  
	public final MarketOrderMessage failedLO;
	public final TradeMessage failedBR;
	public final NegotiateRequestMessage failedTR;
	public final boolean REJECTED;
	
	public AckMessage() {
		super(null);
		this.failedBR = null;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = true;
	}
	
	/**
	 * Rejection for registration
	 * @param registration
	 * @param b : accepted/rejected
	 */
	public AckMessage(RegistrationMessage registration, boolean rejected) {
		super(null);
		this.failedBR = null;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = rejected;
	}
	
	/**
	 * Rejection for a trade;
	 * notifies the agent that they sent an invalid bid
	 * @param ID : rejection ID
	 * @param bid : rejected bid
	 */
	public AckMessage(Integer ID, TradeMessage bid, boolean rejected) {
		super(ID);
		this.failedBR = bid;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = rejected;
	}
	
	/**
	 * Rejection for a negotiation request;
	 * notifies the agent that they sent an invalid buy/sell offer 
	 * @param ID : rejection ID
	 * @param tr : rejected negotiate request
	 */
	public AckMessage(Integer ID, NegotiateRequestMessage tr, boolean rejected) {
		super(ID);
		this.failedBR = null;
		this.failedTR = tr;
		this.failedLO = null;
		this.REJECTED = rejected;
	}
	
	/**
	 * Rejection for limit orders
	 * @param ID : rejection ID
	 * @param lo : rejected limit order
	 */
	public AckMessage(Integer ID, MarketOrderMessage lo, boolean rejected) {
		super(ID);
		this.failedLO = lo;
		this.failedBR = null;
		this.failedTR = null;
		this.REJECTED = rejected;
	}
	
	@Override
	public void dispatch(AbsAgent agent) {
		agent.onAck(this);
	}

}
