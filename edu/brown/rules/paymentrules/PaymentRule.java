package brown.rules.paymentrules;

import brown.marketinternalstates.MarketInternalState;


public interface PaymentRule {

  public void getPayments(MarketInternalState state); 
  
  public void setPaymentType(MarketInternalState state);
  
  public void setReserve(MarketInternalState state); 
  
  public void permitShort(MarketInternalState state);
  
}
