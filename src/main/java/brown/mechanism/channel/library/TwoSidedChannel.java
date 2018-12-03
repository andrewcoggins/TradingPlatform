package brown.mechanism.channel.library;

import brown.mechanism.bid.IGameAction;
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
public class TwoSidedChannel extends AbsChannel{
  private IOrderBook book;
  private Boolean isTest;

  
  /**
   * For Kryo
   * DO NOT USE
   */
  public TwoSidedChannel() {
    super();
    this.book = new OrderBook();
    this.isTest= null;
  }

  /**
   * Constructor
   * @param ID
   */
  public TwoSidedChannel(Integer ID, IOrderBook book, boolean isTest) {
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
  public void bid(AbsAgent agent, IGameAction bid) {
    agent.CLIENT.sendTCP(new TradeMessage(0,bid,this.ID,agent.ID));
  }
  
}
