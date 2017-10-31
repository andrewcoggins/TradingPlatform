package brown.messages.auctions;

import brown.agent.Agent;
import brown.messages.Message;

/**
 * for the lemonade game, to be sent across the network.
 * @author andrew
 *
 */
public class LemonadeBid extends Message {

  public final Integer position; 
  
  public LemonadeBid(Integer ID, Integer lemonadePosition, Integer auctionID, Integer agentID) {
    super(ID);
    this.position = lemonadePosition;
    // TODO Auto-generated constructor stub
  }

  @Override
  public void dispatch(Agent agent) {
    // noop
  }
}