package brown.agent; 

import java.util.LinkedList;
import java.util.List;

import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

public abstract class AbsVCGAgent extends AbsSimpleSealedAgent  {

  protected List<ITradeable> tradeables;
  protected IValuation valuation; 
  protected IValuationDistribution vDistribution; 
  
  
  public AbsVCGAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
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