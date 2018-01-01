package brown.channels.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.AbsBidBundle;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.SimpleBidBundle;
import brown.agent.AbsAgent;
import brown.agent.AbsLemonadeAgent;
import brown.agent.library.LemonadeAgent;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;
import brown.messages.library.BidMessage;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;

/**
 * The channel through which an agent communicates to the server.
 * @author andrew
 *
 */
public class LemonadeChannel implements IAgentChannel {

  private final Integer ID; 
  private Ledger ledger;
  
  
  public LemonadeChannel() { 
    this.ID = null; 
    this.ledger = null;
  }
  /**
   * Do we need other params, such as a bidbundle, an int for eligibility 
   * as for the mechanism type and payment type, do we need them? Better to send un-needed information...
   * @param ID
   * @param ledger
   * @param type
   */
  public LemonadeChannel(Integer ID, Ledger ledger, PaymentType pType, MechanismType type) {
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
    if(agent instanceof AbsLemonadeAgent) {

      AbsLemonadeAgent lemAgent = (AbsLemonadeAgent) agent; 
      lemAgent.onLemonade(this);   
    }
  }
  

  
  public void bid(AbsAgent agent, Integer position) {
    Map<Tradeable, MarketState> bids = new HashMap<Tradeable, MarketState>();
    bids.put(new Tradeable(0), new MarketState(agent.ID, (double) position));
    AbsBidBundle toSend = new SimpleBidBundle(bids);
    agent.CLIENT.sendTCP(new BidMessage(0, toSend, this.ID, agent.ID));
  }
  
}