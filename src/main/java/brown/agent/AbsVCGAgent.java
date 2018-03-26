package brown.agent; 

import java.util.LinkedList;
import java.util.List;

import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.XORValuation;

public abstract class AbsVCGAgent extends AbsAgent  {

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
      
      XORValuation x = (XORValuation) this.valuation; 
      for(ComplexTradeable bundle : x.getTradeables()) { 
        Logging.log("Agent " + this.ID + ", Bundle: " + bundle + ", Value: " + x.getValuation(bundle));
      }
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  }







}