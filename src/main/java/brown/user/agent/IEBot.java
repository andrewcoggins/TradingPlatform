package brown.user.agent;



import java.util.HashSet;
import java.util.List;
import java.util.Set;

import brown.mechanism.channel.CallMarketChannel;


/**
 * Prediction Market Bot.
 * 
 * @author Enrique Areyan Viqueira
 * @author Ishaan Agarwal
 */
public class IEBot extends AbsLab06Agent {

  /**
   * Initial fair value of the contract.
   */
  protected double myInitialFairValue;

  Set<Integer> myBuyBidHist;

  Set<Integer> mySellBidHist;


  /**
   * Constructor.
   * 
   * @param host
   * @param port
   * @throws AgentCreationException
   */
  public IEBot(String host, int port) {
    super(host, port);
    myBuyBidHist = new HashSet<Integer>();
    mySellBidHist = new HashSet<Integer>();

  }

  public IEBot(String host, int port, String name) {
    super(host, port,name);
    myBuyBidHist = new HashSet<Integer>();
    mySellBidHist = new HashSet<Integer>();

  }
  


  @Override
  public void onMarketStart() {

    // TODO Auto-generated method stub
    this.myInitialFairValue = IELib.getFairValue(this.getCoin(), this.getNumDecoys());
    System.out.println("My Coin = " + this.getCoin() + ", my Num Decoys = "
        + this.getNumDecoys() + ", my Initial Fair Value = "
        + this.myInitialFairValue + ", myBalance monies = "
        + this.monies);
    
  }

  @Override
  public void onMarketRequest(CallMarketChannel channel) {

    // TODO Auto-generated method stub
    

    List<CoinInfo> marketInfo = IELib.BookAnalysis(channel.getOrderBook().getBuys().toArray(),
        channel.getOrderBook().getSells().toArray(), myBuyBidHist, mySellBidHist);

    double epsilon = IELib.epsilon(this.getNumDecoys(), marketInfo);

    marketInfo.add(new CoinInfo(this.getCoin(), this.getNumDecoys()));

    double currentFairValue = IELib.formula(marketInfo);

    int myBuyValue = (int) Math.floor(currentFairValue - epsilon);
    int mySellValue = (int) Math.ceil(currentFairValue + epsilon);

    int highestBuy = 0;
    int lowestSell = 100;
    if (channel.getOrderBook().getBuys().size()>0) {
      
       highestBuy = channel.getOrderBook().getBuys().peek().price.intValue();
    }
    if (channel.getOrderBook().getSells().size()>0) {
       lowestSell = channel.getOrderBook().getSells().peek().price.intValue();

    }

    int myBuyBid = Math.min(myBuyValue, highestBuy + 2);
    int mySellBid = Math.max(mySellValue, lowestSell - 2);

    myBuyBid = Math.min(myBuyBid, 99);
    mySellBid = Math.max(mySellBid, 1);

    System.out.println("currentBank = " + this.monies);
    System.out.println(
        "currentFairValue = " + currentFairValue + ", Epsilon = " + epsilon
            + ", highestBuy = " + highestBuy + ", lowestSell = " + lowestSell
            + " \n\t myBuyBid = " + myBuyBid + ", mySellBid = " + mySellBid);
  
    this.buy(myBuyBid, 1, channel);
    this.sell(mySellBid, 1, channel);
    

    myBuyBidHist.add(myBuyBid);
    mySellBidHist.add(mySellBid);


    this.sell(100, 5, channel);
    
  }

  @Override
  public void onTransaction(int quantity, double price) {

    // TODO Auto-generated method stub
    
  }

  /**
   * Runner.
   * 
   * @param args
   * @throws AgentCreationException
   */
  public static void main(String[] args) {

    System.out.println("Init agent");
    new IEBot("localhost", 2121,"IEBot");
    while (true) {
    }
  }

}
