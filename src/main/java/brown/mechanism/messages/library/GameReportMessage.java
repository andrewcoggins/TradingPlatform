package brown.mechanism.messages.library;

import java.util.Map;

import brown.user.agent.library.AbsAgent;

/**
 * The markets can also send agents game reports (e.g., summary statistics) at
 * other times. For example, in a simulation of two sequential second-price
 * auctions, a GameReport might be sent between the two.

 * @author andrew
 *
 */
public abstract class GameReportMessage extends AbsMessage {
	
  /*
   * void kryo
   */
	public GameReportMessage() {
		super(null);
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onGameReport(this);
	}

  public abstract GameReportMessage sanitize(Integer agent, Map<Integer,Integer> privateToPublic);
}
