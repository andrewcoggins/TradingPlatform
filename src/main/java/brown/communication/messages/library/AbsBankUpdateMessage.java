package brown.communication.messages.library;

import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.tradeable.ITradeable;
import brown.user.agent.IAgent;

public abstract class AbsBankUpdateMessage extends AbsServerToAgentMessage implements IBankUpdateMessage {

  private Map<String, List<ITradeable>> tradeablesAdded; 
  private Map<String, List<ITradeable>> tradeablesLost; 
  private Double money; 
  
  public AbsBankUpdateMessage(Integer messageID, Integer agentID, Map<String, List<ITradeable>> tradeablesAdded,
      Map<String, List<ITradeable>> tradeablesLost, Double money) {
    super(messageID, agentID);
    this.tradeablesAdded = tradeablesAdded; 
    this.tradeablesLost = tradeablesLost; 
    this.money = money; 
  }
  
  public Map<String, List<ITradeable>> getTradeablesAdded() {
    return this.tradeablesAdded; 
  }
  
  public Map<String, List<ITradeable>> getTradeablesLost() {
    return this.tradeablesLost; 
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
    return "AbsBankUpdateMessage [tradeablesAdded=" + tradeablesAdded
        + ", tradeablesLost=" + tradeablesLost + ", money=" + money + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((money == null) ? 0 : money.hashCode());
    result = prime * result
        + ((tradeablesAdded == null) ? 0 : tradeablesAdded.hashCode());
    result = prime * result
        + ((tradeablesLost == null) ? 0 : tradeablesLost.hashCode());
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
    if (money == null) {
      if (other.money != null)
        return false;
    } else if (!money.equals(other.money))
      return false;
    if (tradeablesAdded == null) {
      if (other.tradeablesAdded != null)
        return false;
    } else if (!tradeablesAdded.equals(other.tradeablesAdded))
      return false;
    if (tradeablesLost == null) {
      if (other.tradeablesLost != null)
        return false;
    } else if (!tradeablesLost.equals(other.tradeablesLost))
      return false;
    return true;
  }
 
}
