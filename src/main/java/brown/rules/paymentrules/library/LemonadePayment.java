package brown.rules.paymentrules.library; 



import brown.market.marketstate.IMarketState;
import brown.rules.paymentrules.IPaymentRule;
import brown.todeprecate.PaymentType;

public class LemonadePayment implements IPaymentRule {

  @Override
  public void setPayments(IMarketState state) {
    //for the lemonade game, payments are handled in the allocation rule. 
  }

  @Override
  public void setPaymentType(IMarketState state) {
    state.setPaymentType(PaymentType.Lemonade);
  }

  @Override
  public void setReserve(IMarketState state) {
    // TODO Auto-generated method stub
  }

  @Override
  public void permitShort(IMarketState state) {
    state.setShort(true);
  } 
  
}