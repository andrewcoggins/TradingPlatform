package brown.messages.library;


import brown.agent.AbsAgent;
import brown.tradeable.ITradeable;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdateMessage extends AbsMessage {
  
	public final ITradeable tradeableAdded; 
	public final ITradeable tradeableLost;
	public final Double moniesChanged; 
	public final Double quantity;
	
	/*
	 * null kryo
	 */
	public BankUpdateMessage() {
		super(null);
		this.tradeableAdded = null; 
		this.tradeableLost = null;
		this.moniesChanged = null;
		this.quantity = null;
	}

/**
 * constructor for bank update message
 * @param ID
 * private agent id
 * @param tradeableAdded
 * tradeables added to agent account
 * @param tradeableLost
 * tradeables lost from agent account
 * @param moniesAdded
 * monies added to or removed from agent account
 */
	public BankUpdateMessage(int ID, ITradeable tradeableAdded, ITradeable tradeableLost, 
	    double moniesChanged, double quantity) {
		super(ID);
		this.tradeableAdded = tradeableAdded; 
		this.tradeableLost = tradeableLost; 
		this.moniesChanged = moniesChanged; 
		this.quantity = quantity;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onBankUpdate(this);
	}

  @Override
  public String toString() {
    return "BankUpdateMessage [tradeableAdded=" + tradeableAdded
        + ", tradeableLost=" + tradeableLost + ", moniesChanged="
        + moniesChanged + ", quantity=" + quantity + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((moniesChanged == null) ? 0 : moniesChanged.hashCode());
    result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
    result = prime * result
        + ((tradeableAdded == null) ? 0 : tradeableAdded.hashCode());
    result = prime * result
        + ((tradeableLost == null) ? 0 : tradeableLost.hashCode());
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
    BankUpdateMessage other = (BankUpdateMessage) obj;
    if (moniesChanged == null) {
      if (other.moniesChanged != null)
        return false;
    } else if (!moniesChanged.equals(other.moniesChanged))
      return false;
    if (quantity == null) {
      if (other.quantity != null)
        return false;
    } else if (!quantity.equals(other.quantity))
      return false;
    if (tradeableAdded == null) {
      if (other.tradeableAdded != null)
        return false;
    } else if (!tradeableAdded.equals(other.tradeableAdded))
      return false;
    if (tradeableLost == null) {
      if (other.tradeableLost != null)
        return false;
    } else if (!tradeableLost.equals(other.tradeableLost))
      return false;
    return true;
  }
  
}
