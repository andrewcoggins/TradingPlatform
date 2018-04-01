package brown.agent.library; 

import brown.agent.AbsSMRAAgent;
import brown.channels.library.AuctionChannel;
import brown.channels.library.OpenOutcryChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.setup.ISetup;

public class SMRADemoAgent extends AbsSMRAAgent {

  public SMRADemoAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }

  @Override
  public void onOpenOutcry(OpenOutcryChannel channel) {
    // TODO decide how to bid in price discovery rounds.
    
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
    // TODO decide how to bid in settlement round.
    
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    // TODO decide what to do with information.
    
  }
}