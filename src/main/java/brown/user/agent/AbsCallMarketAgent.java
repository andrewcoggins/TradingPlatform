package brown.user.agent;

import brown.logging.library.Logging;
import brown.platform.messages.PredictionMarketValuationMessage;
import brown.platform.messages.PrivateInformationMessage;
import brown.system.setup.ISetup;

/**
 * All agents that bid in call markets will extend this class. 
 * @author acoggins
 *
 */
public abstract class AbsCallMarketAgent extends AbsAgent implements ICallMarketAgent {
	protected Boolean coin;
	protected Integer numDecoys;

	public AbsCallMarketAgent(String host, int port, ISetup gameSetup) {
		super(host, port, gameSetup);
		this.coin = null;
		this.numDecoys = null;
	}

	public AbsCallMarketAgent(String host, int port, ISetup gameSetup, String name) {
		super(host, port, gameSetup, name);
		this.coin = null;
		this.numDecoys = null;
	}

	// stores agent tradeables, valuation and valuation distribution.
	@Override
	public void onPrivateInformation(PrivateInformationMessage privateInfo) {
		if (privateInfo instanceof PredictionMarketValuationMessage) {
			// Logging.log("[-] Information Received");
			this.coin = ((PredictionMarketValuationMessage) privateInfo).getCoin();
			this.numDecoys = ((PredictionMarketValuationMessage) privateInfo).getNumberDecoys();
			// Logging.log("COIN: " + this.coin.toString() + ", NUM DECOYS: " + this.numDecoys.toString());
		} else {
			Logging.log("[x] AbsCallMarketAgent: Wrong Kind of PrivateInformation Received");
		}
	}
}
