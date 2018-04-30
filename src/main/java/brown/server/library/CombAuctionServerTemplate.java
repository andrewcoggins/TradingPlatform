package brown.server.library; // TODO: change this to your package

public class CombAuctionServerTemplate {
  private static int initDelay = 5;  // time to wait before beginning the simulation
  private static int lag = 1000;      // time between intervals in which bots can trade, should probably leave this high to be safe
  private static int nSims = 1; // number of simulations
  private static double increment = 5.; // how much prices increment between rounds

  public static void main(String[] args) throws InterruptedException {
    CombAuctionServer server = new CombAuctionServer(initDelay, lag, nSims, increment, "testfile");
    server.runAll();
  }
}