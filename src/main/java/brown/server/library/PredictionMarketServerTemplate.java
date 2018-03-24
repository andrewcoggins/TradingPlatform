package brown.server.library;

public class PredictionMarketServerTemplate {
  static private int seconds = 30; 
  static private int nSims = 10; 
  static private int init_delay = 5;
  static private int lag = 50;
  
  public static void main(String[] args) throws InterruptedException {
    
    PredictionMarketServer server = new PredictionMarketServer(seconds,nSims,init_delay,lag);
    server.runAll();
  }
}
