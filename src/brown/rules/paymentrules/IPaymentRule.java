package brown.rules.paymentrules;

import brown.market.marketstate.IMarketState;


public interface IPaymentRule {

  public void getPayments(IMarketState state); 
  
  public void setPaymentType(IMarketState state);
  
  public void setReserve(IMarketState state); 
  
  public void permitShort(IMarketState state);
  
}
