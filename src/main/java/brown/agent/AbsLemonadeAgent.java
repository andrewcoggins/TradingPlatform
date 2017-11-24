package brown.agent;

import brown.channels.agent.library.CDAAgentChannel;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdate;
import brown.messages.library.BidRequest;
import brown.messages.library.GameReport;
import brown.messages.library.NegotiateRequest;
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
  public void onMarketUpdate(GameReport marketUpdate) {
    // TODO Auto-generated method 
    
  }


  @Override
  public void onTradeRequest(BidRequest bidRequest) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onNegotiateRequest(NegotiateRequest tradeRequest) {
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