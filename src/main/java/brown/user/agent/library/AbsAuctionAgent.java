package brown.user.agent.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IValuation;
import brown.logging.library.Logging;
import brown.mechanism.messages.library.PrivateInformationMessage;
import brown.mechanism.messages.library.ValuationInformationMessage;
import brown.mechanism.tradeable.ITradeable;
import brown.system.setup.ISetup;
import brown.user.agent.ISimpleSealedAgent;

/**
 * abstract agent for simple sealed auctions. 
 * All agents that bid in simple sealed auctions will extend this class.
 * @author acoggins
 *
 */
public abstract class AbsAuctionAgent extends AbsAgent implements ISimpleSealedAgent {
  
  protected List<ITradeable> tradeables; 
  protected IValuation valuation;
  protected IValuationDistribution vDistribution; 
  
  public AbsAuctionAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
    this.tradeables = new LinkedList<ITradeable>();
  }
  
  public AbsAuctionAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup, name);
    this.tradeables = new LinkedList<ITradeable>();    
  }  
  
  // stores agent tradeables, valuation and valuation distribution.
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage) {
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
      
      for (ITradeable t: this.tradeables){
        Logging.log("Agent " + this.ID + ", Good: " + t.toString() + ", Value: " +this.valuation.getValuation(t));
      }      
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  }  
  
}