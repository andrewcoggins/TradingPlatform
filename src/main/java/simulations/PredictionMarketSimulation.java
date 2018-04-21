package simulations;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.CallMarket;
import brown.market.preset.library.PredictionMarketSettlement;
import brown.server.library.CallMarketServer;
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
	private static int marketLength = 30;
	
	private static String[] botClasses = new String[] {
			"brown.agent.library.RandomAgent",
			"brown.agent.library.FixedAgent",
			"brown.agent.library.UpdateAgent",
			"brown.agent.library.IEBot"
	};
	
	private static int numBots = 5;
	private static String host = "localhost";
	private static int port = 2121;
	
	private static String agentClass;

	public static void main(String[] args) {
		
		for (int t = 0; t < 4; t++) {
			ServerRunnable sr
		}
		
		Thread serverThread = new Thread(new ServerRunnable());
		Thread botsThread = new Thread(new BotsRunnable());
	
		serverThread.start();
		botsThread.start();
	}
	
	public static class ServerRunnable implements Runnable {
		
		private int tier;
		
		@Override
		public void run() {
			CallMarketServer server = new CallMarketServer(marketLength, numSims, delayTime, lag, port + tier);
			try {
				server.runAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public void setTier(int tier) {
			this.tier = tier;
		}
	}
	
	public static class BotsRunnable implements Runnable {
		private int tier;
		
		@Override
		public void run() {
			try {
				String botClass = botClasses[tier];
				Class<?> cl = Class.forName(botClass);
				Constructor<?> cons = cl.getConstructor(String.class, Integer.class, String.class);
				
				for (int i = 0; i < numBots; i++) {
					cons.newInstance(host, port + tier, botClass + i);
				}
				
				while (true) {}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void setTier(int tier) {
			this.tier = tier;
		}
	}
	
	public static class AgentRunnable implements Runnable {
		
		private int tier;
		
		@Override
		public void run() {
			try {
				Class<?> cl = Class.forName(agentClass);
				Constructor<?> cons = cl.getConstructor(String.class, Integer.class);
				cons.newInstance(host, port + tier);
				
				while (true) {}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void setTier(int tier) {
			this.tier = tier;
		}
	}
}
