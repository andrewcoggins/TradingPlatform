package brown.server.library; // TODO: change this to your package

public class PredictionMarketServerTemplate {
	private static int seconds = 30; 	// number of seconds per round
	private static int nSims = 100; 		// number of rounds to run
	private static int init_delay = 5; 	// time to wait before beginning the simulation
	private static int lag = 25;			// time between intervals in which bots can trade
  
	public static void main(String[] args) throws InterruptedException {
		PredictionMarketServer server = new PredictionMarketServer(seconds, nSims, init_delay, lag);
		server.runAll();
	}
}