package brown.messages.library;

import java.util.List;

import brown.agent.AbsAgent;
import brown.tradeable.ITradeable;

public class AccountResetMessage extends AbsMessage{

  public final List<ITradeable> tradeables;
  public final Double monies; 
  
  public AccountResetMessage() {
    super(null);
    this.tradeables = null; 
    this.monies = null;
  }

  public AccountResetMessage(int ID, List<ITradeable> tradeables, double monies) {
    super(ID);
    this.tradeables = tradeables;
    this.monies = monies;
  }

  @Override
  public void dispatch(AbsAgent agent) {
    agent.onAccountResetMessage(this);    
  }

  @Override
  public String toString() {
    return "AccountResetMessage [tradeables=" + tradeables + ", monies="
        + monies + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((monies == null) ? 0 : monies.hashCode());
    result =
        prime * result + ((tradeables == null) ? 0 : tradeables.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AccountResetMessage other = (AccountResetMessage) obj;
    if (monies == null) {
      if (other.monies != null)
        return false;
    } else if (!monies.equals(other.monies))
      return false;
    if (tradeables == null) {
      if (other.tradeables != null)
        return false;
    } else if (!tradeables.equals(other.tradeables))
      return false;
    return true;
  }
}
