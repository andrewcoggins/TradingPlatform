package brown.channels.agent.library;

import brown.accounting.Ledger;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.GameBidBundle;
import brown.agent.AbsAgent;
import brown.agent.AbsLemonadeAgent;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;
import brown.messages.library.TradeMessage;
import brown.todeprecate.PaymentType;

/**
 * The channel through which an agent communicates to the server in the lemonade game.
 * @author andrew
 */
public class LemonadeChannel implements IAgentChannel {

  private final Integer ID; 
  private Ledger ledger;
  
  public LemonadeChannel() { 
    this.ID = null; 
    this.ledger = null;
  }
  
  /**
   * Actual constructor
   * @param ID
   * @param ledger
   */
  public LemonadeChannel(Integer ID, Ledger ledger) {
    this.ID = ID; 
    this.ledger = ledger;
  }

  @Override
  public Integer getAuctionID() {
    // TODO Auto-generated method stub
    return this.ID;
  }

  @Override
  public Ledger getLedger() {
    // TODO Auto-generated method stub
    return this.ledger;
  }

  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsLemonadeAgent) {
      AbsLemonadeAgent lemonadeAgent = (AbsLemonadeAgent) agent; 
      lemonadeAgent.onLemonade(this);   
    }
  }
  
  public void bid(AbsAgent agent, Integer position) {
    IBidBundle toSend = new GameBidBundle(position);
    agent.CLIENT.sendTCP(new TradeMessage(0, toSend, this.ID, agent.ID));
  }
  
}