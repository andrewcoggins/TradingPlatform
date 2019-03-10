package brown.communication.messages.library;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.tradeable.ITradeable;

/**
 * Message provided to agents when their accounts change
 */
public class AccountInitializationMessage extends AbsBankUpdateMessage implements IBankUpdateMessage {

  private Integer agentID; 
  private Map<String, List<ITradeable>> tradeablesAdded; 
  private Double money; 
   
  public AccountInitializationMessage() {
    super(null, null, null, null, null); 
  }
  
  public AccountInitializationMessage(Integer messageID, Integer agentID,
      Map<String, List<ITradeable>> tradeablesAdded, Double money) {
    super(messageID, agentID, tradeablesAdded, new HashMap<String, List<ITradeable>>(), money);
    this.agentID = agentID; 
    this.tradeablesAdded = tradeablesAdded; 
    this.money = money; 
  }

  @Override
  public String toString() {
    return "[x] Account Initialized: [agentID: " + agentID + ", tradeables: " + tradeablesAdded +  ", money: " + money + "]";
  }
}
