package brown.messages.library;

import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

/**
 * This message provides an avenue for agents to
 * either accept or decline a trade request. The initiating agent
 * can also cancel the TR with this method.
 */
public class Trade extends AbsMessage {
	public final NegotiateRequest tradeRequest;
	public final boolean accept;

	public Trade(int ID, NegotiateRequest tradeRequest, boolean accept) {
		super(ID);
		this.tradeRequest = tradeRequest;
		this.accept = accept;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		//Noop
	}

}
