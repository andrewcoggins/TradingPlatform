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
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;

/**
 * an honest agent... bids their valuation. What else is an honest agent to do?
 * 
 * @author andrewcoggins
 *
 */
public class SimpleFPAgent extends AbsFictitiousPlayAgent implements IAgent {

  private IGeneralValuation agentValuation;

  public SimpleFPAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  public SimpleFPAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup, name);
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    UserLogging
        .log("[+] Simulation Json File Name: " + this.simulationJsonFileName);
    UserLogging.log("[+] Information Message Received");
    UserLogging.log(informationMessage);
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    UserLogging.log("[+] Trade Request Message Received");

    Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
    List<IItem> bidItems = new LinkedList<IItem>();

    bidItems.add(new Item("testItem", 1));

    ICart bidCart = new Cart(bidItems);
    bidMap.put(bidCart, agentValuation.getValuation(bidCart));
    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
    ITradeMessage tradeMessage = new TradeMessage(0, this.ID,
        tradeRequestMessage.getAuctionID(), oneSided);
    UserLogging.log(tradeMessage);
    this.CLIENT.sendTCP(tradeMessage);
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    UserLogging.log("[+] Valuation Message Received");
    UserLogging.log(valuationMessage.toString());
    this.agentValuation = valuationMessage.getValuation();
    initiateFictitiousPlay();
  }

  private void initiateFictitiousPlay() {
    Map<String, String> allAgents = new HashMap<String, String>();
    // TODO: allow for private class agents to play fictitious play game too.
    // TODO: add some more parameters for timing
    // TODO: change parameters. 
    // TODO: typesig for fictitious play. 
    // 
    allAgents.put("FPAgentOne", "brown.user.agent.library.SimpleFPAgent");
    doFictitiousPlay(allAgents);
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    System.out.println(reportMessage);

  }

  public static void main(String[] args) {
    new SimpleFPAgent("localhost", 2121, new Setup(), "alice");
    while (true) {
    }
  }

}
