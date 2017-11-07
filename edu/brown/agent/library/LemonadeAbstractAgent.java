package brown.agent.library;

import brown.agent.Agent;
import brown.channels.library.CDAAgentChannel;
import brown.channels.library.LemonadeChannel;
import brown.channels.library.SimpleAuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.BankUpdate;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.messages.trades.NegotiateRequest;
import brown.setup.Setup;

public abstract class LemonadeAbstractAgent extends Agent {

  //TODO: fix up some of these methods. But the sending side is all good.
  public LemonadeAbstractAgent(String host, int port, Setup gameSetup)
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
  public void onSimpleSealed(SimpleAuctionChannel simpleWrapper) {
    //noop
    
  }

  @Override
  public void onSimpleOpenOutcry(SimpleAuctionChannel simpleWrapper) {
    //noop
    
  }

  @Override
  public void onContinuousDoubleAuction(CDAAgentChannel market) {
    //noop
  }
  
}