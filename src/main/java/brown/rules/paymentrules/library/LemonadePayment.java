package brown.rules.paymentrules.library; 



import brown.market.marketstate.ICompleteState;
import brown.rules.paymentrules.IPaymentRule;
import brown.todeprecate.PaymentType;

public class LemonadePayment implements IPaymentRule {

  @Override
  public void setPayments(ICompleteState state) {
    //for the lemonade game, payments are handled in the allocation rule. 
  }

  @Override
  public void setPaymentType(ICompleteState state) {
    state.setPaymentType(PaymentType.Lemonade);
  }

  @Override
  public void setReserve(ICompleteState state) {
    // TODO Auto-generated method stub
  }

  @Override
  public void permitShort(ICompleteState state) {
    state.setShort(true);
  } 
  
}