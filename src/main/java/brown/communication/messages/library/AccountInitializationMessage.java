package brown.communication.messages.library;


import java.util.LinkedList;

import brown.communication.messages.IBankUpdateMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

/**
 * Message provided to agents when their accounts change
 */
public class AccountInitializationMessage extends AbsBankUpdateMessage implements IBankUpdateMessage {
  
  private Integer agentID; 
  private ICart itemsAdded; 
  private Double money; 
  
  public AccountInitializationMessage() {
    super(null, null, null, null, null); 
  }
  
  public AccountInitializationMessage(Integer messageID, Integer agentID,
      ICart itemsAdded, Double money) {
    super(messageID, agentID, itemsAdded, new Cart(new LinkedList<IItem>()), money);
    this.agentID = agentID; 
    this.itemsAdded = itemsAdded; 
    this.money = money; 
  }

  @Override
  public String toString() {
    return "Account Initialized: [agentID=" + agentID + ", itemsAdded="
        + itemsAdded + ", money=" + money + "]";
  }

}
