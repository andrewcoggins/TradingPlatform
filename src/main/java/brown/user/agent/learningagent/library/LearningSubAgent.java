package brown.user.agent.learningagent.library;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.value.valuation.IGeneralValuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.IBankUpdateMessage;
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
import brown.user.agent.IAgent;
import brown.user.agent.library.AbsAgent;

public class LearningSubAgent extends AbsAgent implements IAgent {

    
  private IValuationMessage initialValuation; 
  private IBankUpdateMessage initialEndowment; 
  
  private IGeneralValuation agentValuation; 
  
  public LearningSubAgent(String host, int port, ISetup gameSetup, IValuationMessage valuation, IBankUpdateMessage endowment) {
    super(host, port, gameSetup);
    this.initialValuation = valuation; 
    this.initialEndowment = endowment; 
  }
  
  public LearningSubAgent(String host, int port, ISetup gameSetup, String name, IValuationMessage valuation, IBankUpdateMessage endowment) {
    super(host, port, gameSetup, name);
    this.initialValuation = valuation; 
    this.initialEndowment = endowment; 
  }
  
  
  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    UserLogging.log("[+] Simulation Json File Name: " + this.simulationJsonFileName); 
    UserLogging.log("[+] Information Message Received");
    UserLogging.log("OUTER VALUATION ON THE INSIDE");
    UserLogging.log(informationMessage); 
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
//    UserLogging.log("[+] Trade Request Message Received"); 
    
    Map<ICart, Double> bidMap = new HashMap<ICart, Double>(); 
    List<IItem> bidItems = new LinkedList<IItem>(); 
    
    bidItems.add(new Item("testItem", 1)); 
    
    ICart bidCart = new Cart(bidItems); 
    bidMap.put(bidCart, agentValuation.getValuation(bidCart)); 
    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
    ITradeMessage tradeMessage = new TradeMessage(0, this.ID, tradeRequestMessage.getAuctionID(), oneSided);
    UserLogging.log(tradeMessage); 
    this.CLIENT.sendTCP(tradeMessage); 
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
//    UserLogging.log("[+] Valuation Message Received");
//    UserLogging.log(valuationMessage.toString()); 
    this.agentValuation = this.initialValuation.getValuation(); 
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
    // TODO: update learning data structure. 
//    UserLogging.log("THE AGENT WILL LEARN HERE."); 
//    System.out.println(simReportMessage); 
//    
    // TODO: save and/or update JSON file. 
    
  }

}
