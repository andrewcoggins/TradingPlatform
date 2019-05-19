package brown.communication.messages.library;

import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.item.ICart;
import brown.platform.whiteboard.IWhiteboard;
import brown.user.agent.IAgent;

/**
 * Trade request message is sent by an open market 
 * to an agent to prompt bidding in that market. 
 * An agent typically sends a trademessage in response
 * to a TradeRequestMessage
 * @author andrew
 *
 */
public class TradeRequestMessage extends AbsServerToAgentMessage implements ITradeRequestMessage {

  private BidType bidType; 
  private ICart items; 
  
  public TradeRequestMessage() {
    super(null, null);
    this.bidType = null; 
    this.items = null; 
  }
  
  public TradeRequestMessage(Integer messageID, Integer agentID, BidType bidType, ICart items) {
    super(messageID, agentID);
    this.bidType = bidType; 
    this.items = items; 
  }
  
  @Override
  public BidType getBidType() {
    return this.bidType; 
  }
  
  @Override
  public ICart getItems() {
    return this.items; 
  }

  @Override
  public void addInformation(IWhiteboard whiteboard) {
    // TODO: 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }



}
