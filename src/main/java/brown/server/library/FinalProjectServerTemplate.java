package brown.server.library; // TODO: change this to your package

/**
 * final project server template. Simplifies changeable fields. 
 * @author kerry
 *
 */
public class FinalProjectServerTemplate {
  private static int initDelay = 5;  // time to wait before beginning the simulation
  private static int lag = 50;      // time between intervals in which bots can trade
  private static int nSims = 1;
  private static int nQueryRounds = 30;
  private static double increment = 5.;
  private static int initBundles = 30;
  private static int initMean = 10;
  private static int initStd = 3;

  public static void main(String[] args) throws InterruptedException {
    CombAuctionWithQueryServer server = new CombAuctionWithQueryServer(initDelay, lag, nSims, 
        nQueryRounds, increment, initBundles, initMean, initStd, "/Users/Kerry/Documents/Brown/HTA_1951k/testfile");
    server.runAll();
  }
}