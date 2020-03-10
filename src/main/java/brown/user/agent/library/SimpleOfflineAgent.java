package brown.user.agent.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.user.agent.IAgent;

public class SimpleOfflineAgent extends AbsOfflineAgent implements IAgent {
  
  private IGeneralValuation agentValuation; 
  
  public SimpleOfflineAgent(IOfflineMessageServer messageServer) {
    super(messageServer);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    System.out.println("[agent] agent notifying server of information message: " + informationMessage); 
    this.notifyServer("InformationMessage"); 
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) { 
    Map<ICart, Double> bidMap = new HashMap<ICart, Double>(); 
    List<IItem> bidItems = new LinkedList<IItem>(); 
    
    bidItems.add(new Item("testItem", 1)); 
    
    ICart bidCart = new Cart(bidItems); 
    bidMap.put(bidCart, agentValuation.getValuation(bidCart)); 
    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
    ITradeMessage tradeMessage = new TradeMessage(0, this.ID, tradeRequestMessage.getAuctionID(), oneSided);
    this.messageServer.onBid(this, tradeMessage);
    System.out.println("[agent] agent notifying server of trade request message: " + tradeRequestMessage); 
    this.notifyServer("TradeRequestMessage");
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    this.agentValuation = valuationMessage.getValuation(); 
    System.out.println("[agent] agent notifying server of valuation message: " + valuationMessage); 
    this.notifyServer("ValuationMessage"); 
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    System.out.println("[agent] agent notifying server of simulation report message: " + reportMessage); 
    this.notifyServer("SimulationReportMessage"); 
  }

  @Override
  public void onStatusMessage(IStatusMessage message) {
    System.out.println("[agent] agent notifying server of status message: " + message); 
    this.notifyServer("StatusMessage"); 
  }


}
