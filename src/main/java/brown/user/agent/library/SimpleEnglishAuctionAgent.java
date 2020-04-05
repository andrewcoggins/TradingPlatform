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
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.user.agent.IAgent;

public class SimpleEnglishAuctionAgent extends AbsAgent implements IAgent {

  private IGeneralValuation agentValuation;

  public SimpleEnglishAuctionAgent(String name) {
    super(name);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    System.out.println("[x] information message received");
    System.out.println(informationMessage);
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    
    System.out.println(tradeRequestMessage); 
    Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
    List<IItem> bidItems = new LinkedList<IItem>();

    bidItems.add(new Item("testItem", 1));

    ICart bidCart = new Cart(bidItems);
    // TODO: check the market state via the trade message. 
    bidMap.put(bidCart, agentValuation.getValuation(bidCart));
    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
    ITradeMessage tradeMessage =
        new TradeMessage(0, this.agentBackend.getPrivateID(),
            tradeRequestMessage.getAuctionID(), oneSided);
    
    this.agentBackend.sendMessage(tradeMessage);
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    System.out.println("[x] valuation message received");
    System.out.println(valuationMessage);
    this.agentValuation = valuationMessage.getValuation(); 
    
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    System.out.println("[x] sim report message received");
    System.out.println(reportMessage);
  }


}
