package brown.channels.library; 

import java.util.Map;

import brown.agent.AbsAgent;
import brown.agent.AbsOpenOutcryAgent;
import brown.agent.AbsSpecValAgent;
import brown.agent.AbsSpecValV2Agent;
import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.IBidBundle;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.IAgentChannel;
import brown.messages.library.TradeMessage;
import brown.tradeable.ITradeable;

/**
 * agent channel for all open outcry auctions and agents.
 * @author kerry
 *
 */
public class OpenOutcryChannel extends AbsChannel implements IAgentChannel {
  private Map<ITradeable, Double> reserve; 

  /**
   * for kryo do not use
   */
  public OpenOutcryChannel() {
    super(); 
  }
  
  /**
   * Constructor
   */
  public OpenOutcryChannel(Integer ID, Map<ITradeable, Double> reserve) {
    super(ID); 
    this.reserve = reserve; 
  }
  
  public Map<ITradeable, Double> getReserve(){
    return this.reserve;
  }
  
  @Override
  public void dispatchMessage(AbsAgent agent) {
    if (agent instanceof AbsOpenOutcryAgent) {
      AbsOpenOutcryAgent openOutcryAgent = (AbsOpenOutcryAgent) agent; 
      openOutcryAgent.onOpenOutcry(this); 
    } else if (agent instanceof AbsSpecValAgent){
      ((AbsSpecValAgent) agent).onClockMarket(this);
    } else if (agent instanceof AbsSpecValV2Agent){
      ((AbsSpecValV2Agent) agent).onClockMarket(this);      
    }
  }

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {    
    // is there a limit on how large these can be? 
    // not very fault tolerant
    Map<ITradeable, BidType> b = ((AuctionBid) bid.getBids()).bids;
    agent.CLIENT.sendTCP(new TradeMessage(0,new AuctionBidBundle(b),this.ID,agent.ID));
  }

  @Override
  public String toString() {
    return "OpenOutcryChannel [reserve=" + reserve + "]";
  }

  @Override
  public IAgentChannel sanitize(Integer agent, Map<Integer, Integer> privateToPublic) {
    return this;
  }
  
}