package brown.user.simulations;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import brown.user.server.PredictionMarketServer;
import brown.user.simulations.PredictionMarketSimulation.AgentRunnable;
import brown.user.simulations.PredictionMarketSimulation.BotsRunnable;
import brown.user.simulations.PredictionMarketSimulation.ServerRunnable;

public class SSSPMarketSimulation {
	private static int numSims = 1;
	private static int delayTime = 2; 
	private static int lag = 50;
	private static int marketLength = 20;
	
	private static String[] botClasses = new String[] {
			"brown.user.agent.library.RandomAgent"
	};
	
	private static int numBots = 5;
	private static String host = "localhost";
	private static int port = 2121;
	private String outFile;	
	private String agentClass;
	
	public SSSPMarketSimulation(String agentClass, String outFile) {
		this.agentClass = agentClass;
		this.outFile = outFile;
	}
	
	 public SSSPMarketSimulation(String agentClass, String outFile, int nSims, int marketTime) {
	    this.agentClass = agentClass;
	    this.outFile = outFile;
	    numSims = nSims;
	    marketLength = marketTime;
	  }

	public void run() throws InterruptedException {
		for (int t = 0; t < numBots - 1; t++) {
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
			
			TimeUnit.SECONDS.sleep(numSims * marketLength + 60);
			st.interrupt();
			bt.interrupt();
	    if (agentClass != null) {
	      at.interrupt();
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
