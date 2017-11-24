package brown.messages.library;

import brown.accounting.Ledger;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

/**
 * The markets can also send agents GameReports (e.g., summary statistics) at
 * other times. For example, in a simulation of two sequential second-price
 * auctions, a GameReports might be sent between the two.

 * @author andrew
 *
 */
public class GameReport extends AbsMessage {
	public final Ledger LEDGER;
	
	public GameReport() {
		super(null);
		this.LEDGER = null;
	}

	public GameReport(Ledger ledger) {
		super(null);
		this.LEDGER = ledger;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onMarketUpdate(this);
	}

}
