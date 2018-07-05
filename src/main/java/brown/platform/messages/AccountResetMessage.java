package brown.platform.messages;

import java.util.List;

import brown.mechanism.tradeable.ITradeable;
import brown.user.agent.AbsAgent;

/**
 * Account reset message notifies an agent when their account has been reset at the 
 * start of a new simulation. The message describes the tradeables and money that
 * the agent ended the simulation with. 
 * @author kerry
 *
 */
public class AccountResetMessage extends AbsMessage {

  public final List<ITradeable> tradeables;
  public final Double monies; 
  
  /**
   * null kryo
   */
  public AccountResetMessage() {
    super(null);
    this.tradeables = null; 
    this.monies = null;
  }

  /**
   * Account reset message initialized with an agent ID, and a list of tradeables
   * and money in account before reset.
   * @param ID agent ID
   * @param tradeables tradeables in account
   * @param monies money in account 
   */
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
