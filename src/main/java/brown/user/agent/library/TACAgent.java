package brown.user.agent.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.TradeMessage;
import brown.logging.library.UserLogging;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;
import brown.platform.item.library.Item;
import brown.system.setup.ISetup;
import brown.user.agent.IAgent;

/**
 * tac agent bids in a TAC auction. 
 * selects a minimally feasible package. 
 * 
 * @author andrewcoggins
 *
 */
public class TACAgent extends AbsAgent implements IAgent {

  private IGeneralValuation agentValuation;

  public TACAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  public TACAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup, name);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    UserLogging.log("[+] Information Message Received");
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    UserLogging.log("[+] Trade Request Message Received: " + tradeRequestMessage.toString());

    Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
    List<IItem> bidItems = new LinkedList<IItem>();

    for (IItem anItem : tradeRequestMessage.getItems().getItems()) {
      if (anItem.getName().equals("dayOneArrivalTicket")
          || anItem.getName().equals("dayTwoDepartureTicket")
          || anItem.getName().equals("museumDayOneTicket")) {
        bidItems.add(new Item(anItem.getName(), anItem.getItemCount()));
      }
    }
    
    if (bidItems.size() != 0) {
      ICart bidCart = new Cart(bidItems);
      bidMap.put(bidCart, 10.0);
      IBidBundle oneSided = new OneSidedBidBundle(bidMap);
      ITradeMessage tradeMessage = new TradeMessage(0, this.ID,
          tradeRequestMessage.getAuctionID(), oneSided);
      UserLogging.log(tradeMessage);
      this.CLIENT.sendTCP(tradeMessage);
    }
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    UserLogging.log("[+] Valuation Message Received");
    UserLogging.log(valuationMessage.toString());
    this.agentValuation = valuationMessage.getValuation();
  }

}
