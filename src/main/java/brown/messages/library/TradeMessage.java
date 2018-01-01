package brown.messages.library;

import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

/**
 * This message provides an avenue for agents to
 * either accept or decline a trade request. The initiating agent
 * can also cancel the TR with this method.
 */
public class TradeMessage extends AbsMessage {
	public final NegotiateRequestMessage tradeRequest;
	public final boolean accept;

	public TradeMessage(int ID, NegotiateRequestMessage tradeRequest, boolean accept) {
		super(ID);
		this.tradeRequest = tradeRequest;
		this.accept = accept;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		//Noop
	}

}
