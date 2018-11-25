package brown.mechanism.channel.library;

import java.util.Map;

import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.channel.IAgentChannel;
import brown.platform.messages.library.TradeMessage;
import brown.platform.twosided.IOrderBook;
import brown.platform.twosided.OrderBook;
import brown.user.agent.library.AbsAgent;
import brown.user.agent.library.AbsCallMarketAgent;

/**
 * agent channel used in call market auctions.
 * @author kerry
 *
 */
public class CallMarketChannel extends AbsChannel{
  private IOrderBook book;
  private Boolean isTest;

  
  /**
   * For Kryo
   * DO NOT USE
   */
  public CallMarketChannel() {
    super();
    this.book = new OrderBook();
    this.isTest= null;
  }

  /**
   * Constructor
   * @param ID
   */
  public CallMarketChannel(Integer ID, IOrderBook book, boolean isTest) {
    super(ID);
    this.book = book;
    this.isTest = isTest;
  }
  
  public IOrderBook getOrderBook() {
    return this.book;
  }
  
  public boolean isTest() {
    return this.isTest;
  }
  
  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsCallMarketAgent) {
      AbsCallMarketAgent absCallAgent = (AbsCallMarketAgent) agent; 
      absCallAgent.onCallMarket(this);
      }  
    }

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    agent.CLIENT.sendTCP(new TradeMessage(0,bid,this.ID,agent.ID));
  }
  
  @Override
  public IAgentChannel sanitize(Integer agent, Map<Integer, Integer> privateToPublic) {
    return new CallMarketChannel(this.ID, this.book.sanitize(200,agent, privateToPublic),this.isTest);
  }  
}
