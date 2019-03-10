package brown.communication.messages.library;


import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.tradeable.ITradeable;
import brown.user.agent.library.AbsAgent;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdateMessage extends AbsBankUpdateMessage implements IBankUpdateMessage {

   
  public BankUpdateMessage() {
    super(null, null, null, null, null); 
  }
  
  public BankUpdateMessage(Integer messageID, Integer agentID,
      Map<String, List<ITradeable>> tradeablesAdded,
      Map<String, List<ITradeable>> tradeablesLost, Double money) {
    super(messageID, agentID, tradeablesAdded, tradeablesLost, money);
  }

}
