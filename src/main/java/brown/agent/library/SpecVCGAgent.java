package brown.agent.library; 

import java.util.List;

import brown.agent.AbsVCGAgent;
import brown.agent.ISimpleSealedAgent;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.value.valuation.IValuation;
import brown.value.valuation.library.XORValuation;

public class SpecVCGAgent extends AbsVCGAgent implements ISimpleSealedAgent {

  public SpecVCGAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
    List<ITradeable> allTradeables = this.tradeables; 
    XORValuation valuation = (XORValuation) this.valuation; 
  } 
  
  
}