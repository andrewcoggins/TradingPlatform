package brown.user.simulations;

public class SSSPMarketSimulationTest {
	public static void main(String[] args) {

		SSSPMarketSimulation t = new SSSPMarketSimulation("brown.user.agent.library.RandomAgent",
				"output_prediction_market.txt");
		try {
			t.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
