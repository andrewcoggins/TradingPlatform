package brown.channels.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.agent.Agent;
import brown.agent.library.LemonadeAgent;
import brown.assets.accounting.Ledger;
import brown.bundles.BidBundle;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.channels.IAgentChannel;
import brown.channels.MechanismType;
import brown.messages.auctions.Bid;
import brown.rules.paymentrules.PaymentType;
import brown.valuable.library.Tradeable;

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
    return null;
  }

  @Override
  public Ledger getLedger() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void dispatchMessage(Agent agent) {
    if(agent instanceof LemonadeAgent) {
      LemonadeAgent lemAgent = (LemonadeAgent) agent; 
      lemAgent.onLemonade(this);   
    }
  }
  

  
  public void bid(Agent agent, Integer position) {
    Map<Tradeable, MarketState> bids = new HashMap<Tradeable, MarketState>();
    bids.put(new Tradeable(0), new MarketState(agent.ID, (double) position));
    BidBundle toSend = new SimpleBidBundle(bids);
    agent.CLIENT.sendTCP(new Bid(0, toSend, this.ID, agent.ID));
  }
  
}