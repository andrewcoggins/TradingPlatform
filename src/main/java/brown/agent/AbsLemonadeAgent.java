package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidRequestMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.setup.ISetup;

public abstract class AbsLemonadeAgent extends AbsAgent {

  //TODO: fix up some of these methods. But the sending side is all good.
  public AbsLemonadeAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
    // TODO Auto-generated constructor stub
  }
  
  public abstract void onLemonade(LemonadeChannel channel);

  @Override
  public void onMarketUpdate(GameReportMessage marketUpdate) {
    // TODO Auto-generated method 
    
  }


  @Override
  public void onTradeRequest(BidRequestMessage bidRequest) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onNegotiateRequest(NegotiateRequestMessage tradeRequest) {
    // noop
    
  }

  @Override
  public void onSimpleSealed(SimpleAgentChannel simpleWrapper) {
    //noop
    
  }

  @Override
  public void onSimpleOpenOutcry(SimpleAgentChannel simpleWrapper) {
    //noop
    
  }

  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //noop
  }
  
}