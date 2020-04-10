package brown.user.agent.library;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.communication.messages.ITradeMessage;
import brown.platform.item.library.DemandSet;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;

public class MyAgent extends AbsSpectrumAgent implements IAgent {
	public MyAgent(String name) {
		super(name);
	}

	@Override
	protected void onAuctionStart() {
	}
	
	@Override
	protected void onAuctionEnd(Map<Integer, Set<String>> allocations, Map<Integer, Double> payments,
			List<List<ITradeMessage>> tradeHistory) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected Map<String, Double> getBids(Map<String, Double> prices) {
		Map<String, Double> bids = new HashMap<>();
		for (String good : prices.keySet()) {
			if (this.getValuation(good) >= prices.get(good)) {
				bids.put(good, prices.get(good));
			}
		}
		return bids;
	}

	
	public static void launch(final String name) {
		MyAgent agent = new MyAgent(name);
		agent.addAgentBackend(new OnlineAgentBackend("localhost", 5000, new Setup(), agent));
	}
	
	public static void main(String[] args) {
		launch("jake");
		launch("fred");
		launch("tony");
		launch("jack");
		launch("greg");
		launch("jeff");
		launch("bart");
		while (true) {}
	}

}
