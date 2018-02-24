package brown.channels.library;

import brown.agent.AbsAgent;
import brown.agent.AbsCallMarketAgent;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.logging.Logging;
import brown.market.marketstate.library.OrderBook;
import brown.messages.library.TradeMessage;

public class CallMarketChannel extends AbsChannel{
  private OrderBook book;

  
  /**
   * For Kryo
   * DO NOT USE
   */
  public CallMarketChannel() {
    super();
    this.book = new OrderBook();
  }

  /**
   * Constructor
   * @param ID
   */
  public CallMarketChannel(Integer ID, OrderBook book) {
    super(ID);
    this.book = book;
  }
  
  public OrderBook getOrderBook(){
    return this.book;
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
    if (bid.getType() == BundleType.TWOSIDED){
      agent.CLIENT.sendTCP(new TradeMessage(0,bid,this.ID,agent.ID));
    } else {
      Logging.log("[Channel encountered invalid bid type]");
      return;      
    }   
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
    return "CallMarketChannel [book=" + book + "]";
  }  
}
