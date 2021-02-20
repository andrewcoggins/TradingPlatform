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
import brown.logging.library.UserLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.user.agent.IAgent;

/**
 * an honest agent... bids their valuation. What else is an honest agent to do?
 * 
 * @author andrewcoggins
 *
 */
public class SimpleAgent extends AbsAgent implements IAgent {

  private IGeneralValuation agentValuation;

  public SimpleAgent(String name) {
    super(name);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {

    Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
    List<IItem> bidItems = new LinkedList<IItem>();

    bidItems.add(new Item("testItem", 1));

    ICart bidCart = new Cart(bidItems);
    bidMap.put(bidCart, agentValuation.getValuation(bidCart));
    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
    ITradeMessage tradeMessage =
        new TradeMessage(0, this.agentBackend.getPrivateID(),
            tradeRequestMessage.getAuctionID(), oneSided);
    this.agentBackend.sendMessage(tradeMessage);
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    this.agentValuation = valuationMessage.getValuation();
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    System.out.println(reportMessage);

  }

}
