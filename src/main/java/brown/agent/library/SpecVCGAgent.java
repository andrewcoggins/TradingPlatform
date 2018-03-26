package brown.agent.library; 

import java.util.List;

import brown.agent.AbsVCGAgent;
import brown.agent.ISimpleSealedAgent;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.setup.library.VCGSetup;
import brown.tradeable.ITradeable;
import brown.value.valuation.library.XORValuation;

public class SpecVCGAgent extends AbsVCGAgent implements ISimpleSealedAgent {

  public SpecVCGAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new VCGSetup());
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
  
  public static void main(String[] args) throws AgentCreationException {
    new SpecVCGAgent("localhost", 2121); 
    new SpecVCGAgent("localhost", 2121); 
    while(true){}
  }
  
  
}