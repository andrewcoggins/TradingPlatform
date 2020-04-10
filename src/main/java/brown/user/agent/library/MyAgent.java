package brown.user.agent.library;

import java.util.Map;

import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.platform.item.library.DemandSet;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;

public class MyAgent extends AbsCanadianSpectrumAgent implements IAgent {

	public MyAgent(String name) {
		super(name);
	}

	@Override
	protected void onAuctionStart() {
	}

	@Override
	protected DemandSet getDemandSet(Map<GSVMLicense, Double> prices) {
		// TODO Auto-generated method stub
		DemandSet d = new DemandSet();
		
		for (GSVMLicense good : prices.keySet()) {
			if (this.getValuation(good) >= prices.get(good)) {
				d.add(good);
				System.out.println("BIDDING\t" + good.getId() + "\t" + this.getValuation(good) + "\t" + prices.get(good));
			}
		}
		return d;
	}
	
	public static void launch(final String name) {
		MyAgent agent = new MyAgent(name);
		agent.addAgentBackend(new OnlineAgentBackend("localhost", 5000, new Setup(), agent));
	}
	
	public static void main(String[] args) {
		launch("jake");
		//launch("fred");
		while (true) {}
	}

}
