package brown.communication.messages.library;


import java.util.List;
import java.util.Map;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.item.ICart;
import brown.platform.tradeable.ITradeable;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdateMessage extends AbsBankUpdateMessage implements IBankUpdateMessage {

   
  public BankUpdateMessage() {
    super(null, null, null, null, null); 
  }
  
  public BankUpdateMessage(Integer messageID, Integer agentID,
      ICart itemsAdded,
      ICart itemsLost, Double money) {
    super(messageID, agentID, itemsAdded, itemsLost, money);
  }

}
