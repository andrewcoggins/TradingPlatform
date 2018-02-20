package brown.agent;

import java.util.LinkedList;
import java.util.List;

import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.PrivateInformationMessage;
import brown.messages.library.PredictionMarketInfoMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;

public abstract class AbsCallMarketAgent extends AbsAgent implements ICallMarketAgent {
  protected List<ITradeable> contracts;
  protected Boolean coin;
  protected Integer numDecoys;
  
  public AbsCallMarketAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    this.contracts = new LinkedList<ITradeable>();
    this.coin = null;
    this.numDecoys = null;
  }
  
  // stores agent tradeables, valuation and valuation distribution.
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {   
    if (privateInfo instanceof PredictionMarketInfoMessage) {
      Logging.log("[-] Information Received");
      this.coin = ((PredictionMarketInfoMessage) privateInfo).getCoin();
      this.numDecoys = ((PredictionMarketInfoMessage) privateInfo).getNumDecoys();
    } else {
      Logging.log("[x] AbsCallMarketAgent: Wrong Kind of PrivateInformation Received");
    }
  }
}
