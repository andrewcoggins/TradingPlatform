package brown.channels.library;

import java.util.Map;

import brown.agent.AbsAgent;
import brown.agent.AbsCallMarketAgent;
import brown.bidbundle.IBidBundle;
import brown.channels.IAgentChannel;
import brown.market.marketstate.library.OrderBook;
import brown.messages.library.TradeMessage;

/**
 * agent channel used in call market auctions.
 * @author kerry
 *
 */
public class CallMarketChannel extends AbsChannel{
  private OrderBook book;
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
  public CallMarketChannel(Integer ID, OrderBook book, boolean isTest) {
    super(ID);
    this.book = book;
    this.isTest = isTest;
  }
  
  public OrderBook getOrderBook(){
    return this.book;
  }
  
  public boolean isTest(){
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
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((book == null) ? 0 : book.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    CallMarketChannel other = (CallMarketChannel) obj;
    if (book == null) {
      if (other.book != null)
        return false;
    } else if (!book.equals(other.book))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "CallMarketChannel [" + this.book.getBuys().size() + " buys, " + this.book.getSells().size() + "sells]";
  }

  @Override
  public IAgentChannel sanitize(Integer agent, Map<Integer, Integer> privateToPublic) {
    return new CallMarketChannel(this.ID, this.book.sanitize(200,agent, privateToPublic),this.isTest);
  }  
}
