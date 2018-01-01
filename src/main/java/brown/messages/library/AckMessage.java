package brown.messages.library;

import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

/**
 * message provided to agent when there is an 
 * error on the server relating to a message the agent sent.
 * @author andrew
 *
 */
public class AckMessage extends AbsMessage {
	public final MarketOrderMessage failedLO;
	public final BidMessage failedBR;
	public final NegotiateRequestMessage failedTR;
	public final boolean REJECTED;
	
	/**
	 * Empty for kryo
	 * DO NOT USE
	 */
	public AckMessage() {
		super(null);
		this.failedBR = null;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = true;
	}
	
	/**
	 * Constructor for registration
	 * @param registration 
	 * @param b : accepted/rejected
	 */
	public AckMessage(RegistrationMessage registration, boolean b) {
		super(null);
		this.failedBR = null;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = b;
	}
	
	/**
	 * Rejection for a bid;
	 * notifies the agent that they sent an improper request
	 * @param ID : rejection ID
	 * @param bid : rejected bid
	 */
	public AckMessage(Integer ID, BidMessage bid, boolean rejected) {
		super(ID);
		this.failedBR = bid;
		this.failedTR = null;
		this.failedLO = null;
		this.REJECTED = rejected;
	}
	
	/**
	 * Rejection for a trade request;
	 * notifies the agent that they sent an improper request
	 * @param ID : rejection ID
	 * @param tr : rejected trade request
	 */
	public AckMessage(Integer ID, NegotiateRequestMessage tr, boolean rejected) {
		super(ID);
		this.failedBR = null;
		this.failedTR = tr;
		this.failedLO = null;
		this.REJECTED = rejected;
	}
	
	/**
	 * For rejected limit orders
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
