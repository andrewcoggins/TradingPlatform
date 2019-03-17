package brown.communication.bid.library;

import brown.communication.bid.ICart;

public abstract class AbsCart implements ICart {

  private String tradeableName; 
  private Integer numTradeables; 
  
  public AbsCart(String tradeableName, Integer numTradeables) {
    this.tradeableName = tradeableName; 
    this.numTradeables = numTradeables; 
  }
  
  public String getTradeableName() {
    return this.tradeableName; 
  }
  
  public Integer getNumTradeables() {
    return this.numTradeables; 
  }
  
}
