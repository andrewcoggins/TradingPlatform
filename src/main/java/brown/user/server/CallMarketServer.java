package brown.user.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.CallMarket;
import brown.auction.preset.PredictionMarketSettlement;
import brown.auction.value.manager.library.PredictionMarketDecoysConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.system.setup.library.CallMarketSetup;

/**
 * Server main class for call markets.
 * @author kerry
 *
 */
public class CallMarketServer {
	private int seconds;
	private int numSims;
	private int initDelay;
	private int lag;
	private int port;
	private String outFile;

	public CallMarketServer(int seconds, int numSims, int initDelay, int lag, String outFile) {
		this.seconds = seconds;
		this.numSims = numSims;
		this.initDelay = initDelay;
		this.lag = lag;
		this.port = 2121;
		this.outFile = outFile;
	}

	public CallMarketServer(int seconds, int numSims, int initDelay, int lag, int port, String outFile) {
		this.seconds = seconds;
		this.numSims = numSims;
		this.initDelay = initDelay;
		this.lag = lag;
		this.port = port;
		this.outFile = outFile;
	}

	public void runAll() throws InterruptedException {
		// Create _ tradeables
		Set<ITradeable> allTradeables = new HashSet<ITradeable>();
		List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();

		// only 1 tradeable
		allTradeables.add(new SimpleTradeable(0));
		allTradeablesList.add(new SimpleTradeable(0));

		List<AbsMarketRules> oneMarket = new LinkedList<AbsMarketRules>();
		oneMarket.add(new CallMarket(this.seconds));
		SimulMarkets phase_one = new SimulMarkets(oneMarket);

		List<AbsMarketRules> twoMarket = new LinkedList<AbsMarketRules>();
		twoMarket.add(new PredictionMarketSettlement());
		SimulMarkets phase_two = new SimulMarkets(twoMarket);

		List<SimulMarkets> seq = new LinkedList<SimulMarkets>();
		seq.add(phase_one);
		seq.add(phase_two);

		Simulation testSim = new Simulation(seq, new PredictionMarketDecoysConfig(), allTradeablesList, 0.0,
				new LinkedList<ITradeable>());
		RunServer testServer = new RunServer(port, new CallMarketSetup());

		testServer.runSimulation(testSim, this.numSims, this.initDelay, this.lag, this.outFile);
	}

	public static void main(String[] args) throws InterruptedException {
		CallMarketServer server = new CallMarketServer(8, 1, 5, 25, "Testfile");
		server.runAll();
	}
}
