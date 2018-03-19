package simulations;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.CallMarket;
import brown.market.preset.library.PredictionMarketSettlement;
import brown.server.library.RunServer;
import brown.server.library.SimulMarkets;
import brown.server.library.Simulation;
import brown.setup.library.CallMarketSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.PredictionMarketDecoysConfig;

public class PredictionMarketSimulation {
	private static int numSims = 100;
	private static int delayTime = 5; 
	private static int lag = 50;
	private static double marketLength = 30;
	
	private static String botClass = "brown.agent.library.RandomAgent";
	private static int numBots = 5;
	private static String host = "localhost";
	private static int port = 2121;
	private static String botName = "bot";
	
	private static String agentClass;

	public static void main(String[] args) {
		if (args.length > 0) {
			
		}
		
		Thread serverThread = new Thread(new ServerRunnable());
		Thread botsThread = new Thread(new BotsRunnable());
	
		serverThread.start();
		botsThread.start();
	}
	
	public static class ServerRunnable implements Runnable {
		@Override
		public void run() {
			// Create _ tradeables
			Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
			List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();

			// only 1 tradeable
			allTradeables.add(new SimpleTradeable(0));
			allTradeablesList.add(new SimpleTradeable(0));
			
			List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();          
			oneMarket.add(new CallMarket(marketLength));        
			SimulMarkets phase_one = new SimulMarkets(oneMarket);
			
			List<AbsMarketPreset> twoMarket = new LinkedList<AbsMarketPreset>();
			twoMarket.add(new PredictionMarketSettlement());    
			SimulMarkets phase_two = new SimulMarkets(twoMarket);
			
			List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
			seq.add(phase_one);
			seq.add(phase_two);
			
			Simulation testSim = new Simulation(seq,new PredictionMarketDecoysConfig(),
				allTradeablesList,0.0,new LinkedList<ITradeable>());    
			RunServer testServer = new RunServer(port, new CallMarketSetup());
			
			try {
				testServer.runSimulation(testSim, numSims, delayTime, lag);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class BotsRunnable implements Runnable {
		@Override
		public void run() {
			try {
				Class<?> cl = Class.forName(botClass);
				Constructor<?> cons = cl.getConstructor(String.class, Integer.class, String.class);
				
				for (int i = 0; i < numBots; i++) {
					cons.newInstance(host, port, botName + i);
				}
				
				while (true) {}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class AgentRunnable implements Runnable {
		@Override
		public void run() {
			try {
				Class<?> cl = Class.forName(agentClass);
				Constructor<?> cons = cl.getConstructor(String.class, Integer.class);
				cons.newInstance(host, port);
				
				while (true) {}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
