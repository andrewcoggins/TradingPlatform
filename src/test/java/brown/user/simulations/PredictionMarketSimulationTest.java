package brown.user.simulations;

public class PredictionMarketSimulationTest {

	public static void main(String[] args) {

		PredictionMarketSimulation t = new PredictionMarketSimulation("brown.user.agent.library.RandomAgent",
				"output_prediction_market.txt");
		try {
			t.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
