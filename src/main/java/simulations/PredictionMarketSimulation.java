package simulations;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import brown.server.library.PredictionMarketServer;

public class PredictionMarketSimulation {
	private static int numSims = 1;
	private static int delayTime = 2; 
	private static int lag = 50;
	private static int marketLength = 5;
	
	private static String[] botClasses = new String[] {
			"brown.agent.library.RandomAgent",
			"brown.agent.library.FixedAgent",
			"brown.agent.library.UpdateAgent",
			"brown.agent.library.unshared.IEBot"
	};
	
	private static int numBots = 5;
	private static String host = "localhost";
	private static int port = 2121;
	private String outFile;	
	private String agentClass;
	
	public PredictionMarketSimulation(String agentClass, String outFile) {
		this.agentClass = agentClass;
		this.outFile = outFile;
	}

	public void run() {
		for (int t = 0; t < 4; t++) {
			ServerRunnable sr = new ServerRunnable();
			BotsRunnable br = new BotsRunnable();
			AgentRunnable ar = new AgentRunnable();
			
			sr.setTier(t);
			br.setTier(t);
			ar.setTier(t);
			
			Thread st = new Thread(sr);
			Thread bt = new Thread(br);
			Thread at = new Thread(ar);
			
			st.start();
			bt.start();
			if (agentClass != null) {
				at.start();
			}
		}
	}
	
	public class ServerRunnable implements Runnable {
		
		private int tier;
		
		@Override
		public void run() {
			DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
			String outfile = outFile + agentClass + tier + "-" + df.format(new Date());
			
			PredictionMarketServer server = 
					new PredictionMarketServer(marketLength, numSims, delayTime, lag, port + tier, outfile);
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
				Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE, String.class);
				
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
	
	public class AgentRunnable implements Runnable {
		
		private int tier;
		
		@Override
		public void run() {
			try {
				Class<?> cl = Class.forName(agentClass);
				Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE, String.class);
				cons.newInstance(host, port + tier, agentClass);
				
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
