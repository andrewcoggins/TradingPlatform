package brown.rules.paymentrules.library; 



import brown.marketinternalstates.MarketInternalState;
import brown.rules.paymentrules.PaymentRule;
import brown.rules.paymentrules.PaymentType;

public class LemonadePayment implements PaymentRule {

  @Override
  public void getPayments(MarketInternalState state) {
    //for the lemonade game, payments are handled in the allocation rule. 
  }

  @Override
  public void setPaymentType(MarketInternalState state) {
    state.setPaymentType(PaymentType.Lemonade);
  }

  @Override
  public void setReserve(MarketInternalState state) {
    // TODO Auto-generated method stub
  }

  @Override
  public void permitShort(MarketInternalState state) {
    state.setShort(true);
  } 
  
}