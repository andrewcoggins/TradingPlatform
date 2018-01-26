package brown.agent;

import java.util.LinkedList;
import java.util.List;

import brown.channels.agent.library.SSSPChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.ValuationInformationMessage;
import brown.setup.ISetup;
import brown.setup.Logging;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

public abstract class AbsSSSPAgent extends AbsAgent implements ISSSPAgent{
  
  protected List<ITradeable> tradeables; 
  protected IValuation valuation;
  protected IValuationDistribution vDistribution; 
  
  public AbsSSSPAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    this.tradeables = new LinkedList<ITradeable>();
    System.out.println(this.tradeables);
  }

  public abstract void onSSSP(SSSPChannel channel);
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof ValuationInformationMessage){
      Logging.log("[-] Valuation Info Received");
      this.tradeables = ((ValuationInformationMessage) privateInfo).getTradeables();
      this.valuation = ((ValuationInformationMessage) privateInfo).getPrivateValuation();
      this.vDistribution = ((ValuationInformationMessage) privateInfo).getAllValuations();
    } else {
      Logging.log("[x] AbsSSSPAgent: Wrong Kind of PrivateInformation Received");
    }
  }  
  
}