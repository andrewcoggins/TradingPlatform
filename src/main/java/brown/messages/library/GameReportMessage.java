package brown.messages.library;

import brown.accounting.Ledger;
import brown.agent.AbsAgent;

/**
 * The markets can also send agents game reports (e.g., summary statistics) at
 * other times. For example, in a simulation of two sequential second-price
 * auctions, a GameReport might be sent between the two.

 * @author andrew
 *
 */
public class GameReportMessage extends AbsMessage {
  
	public final Ledger LEDGER;
	
	public GameReportMessage() {
		super(null);
		this.LEDGER = null;
	}

	public GameReportMessage(Ledger ledger) {
		super(null);
		this.LEDGER = ledger;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onMarketUpdate(this);
	}

}
