package brown.agent.library; 

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.agent.AbsVCGAgent;
import brown.agent.IAuctionAgent;
import brown.bid.interim.BidType;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.GameReportMessage;
import brown.setup.library.SpecValSetup;
import brown.setup.library.VCGSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.value.valuation.library.XORValuation;

public class SpecVCGAgent extends AbsVCGAgent implements IAuctionAgent {

  public SpecVCGAgent(String host, int port)
      throws AgentCreationException {
    super(host, port, new SpecValSetup());
  }

  @Override
  public void onGameReport(GameReportMessage gameReport) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onSimpleSealed(AuctionChannel channel) {
    // bids valuation on every bundle.
    Map<ITradeable, BidType> bids = new HashMap<ITradeable, BidType>(); 
    for (ComplexTradeable t : this.XORBids.keySet()) { 
      bids.put(t, new BidType(this.XORBids.get(t), 1));
    }
    AuctionBidBundle aBundle = new AuctionBidBundle(bids);
    channel.bid(this, aBundle); 
  } 
  
  public static void main(String[] args) throws AgentCreationException {
    new SpecVCGAgent("localhost", 2121); 
    new SpecVCGAgent("localhost", 2121); 
    while(true){}
  }

  @Override
  public void onMarketStart() {
  }

  
  
}