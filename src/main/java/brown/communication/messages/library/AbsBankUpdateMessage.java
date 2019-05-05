package brown.communication.messages.library;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.item.ICart;
import brown.user.agent.IAgent;

public abstract class AbsBankUpdateMessage extends AbsServerToAgentMessage implements IBankUpdateMessage {

  private ICart itemsAdded; 
  private ICart itemsLost; 
  private Double money; 
  
  public AbsBankUpdateMessage(Integer messageID, Integer agentID, ICart itemsAdded,
      ICart itemsLost, Double money) {
    super(messageID, agentID);
    this.itemsAdded = itemsAdded;  
    this.itemsLost = itemsLost;  
    this.money = money; 
  }
  
  public ICart getItemsAdded() {
    return this.itemsAdded; 
  }
  
  public ICart getItemsLost() {
    return this.itemsLost; 
  }
  
  public Double getMoneyAddedLost() {
    return this.money; 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    agent.onBankUpdate(this);
  }

  @Override
  public String toString() {
    return "AbsBankUpdateMessage [itemsAdded=" + itemsAdded + ", itemsLost="
        + itemsLost + ", money=" + money + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((itemsAdded == null) ? 0 : itemsAdded.hashCode());
    result = prime * result + ((itemsLost == null) ? 0 : itemsLost.hashCode());
    result = prime * result + ((money == null) ? 0 : money.hashCode());
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
    AbsBankUpdateMessage other = (AbsBankUpdateMessage) obj;
    if (itemsAdded == null) {
      if (other.itemsAdded != null)
        return false;
    } else if (!itemsAdded.equals(other.itemsAdded))
      return false;
    if (itemsLost == null) {
      if (other.itemsLost != null)
        return false;
    } else if (!itemsLost.equals(other.itemsLost))
      return false;
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    return true;
  }
  
}
