//package brown.user.agent.learningagent.library;
//
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.json.simple.JSONObject;
//
//import brown.auction.marketstate.IMarketPublicState;
//import brown.auction.value.valuation.IGeneralValuation;
//import brown.communication.bid.IBidBundle;
//import brown.communication.bid.library.OneSidedBidBundle;
//import brown.communication.messages.IInformationMessage;
//import brown.communication.messages.ISimulationReportMessage;
//import brown.communication.messages.ITradeMessage;
//import brown.communication.messages.ITradeRequestMessage;
//import brown.communication.messages.IValuationMessage;
//import brown.communication.messages.library.TradeMessage;
//import brown.logging.library.UserLogging;
//import brown.platform.accounting.IAccountUpdate;
//import brown.platform.item.ICart;
//import brown.platform.item.IItem;
//import brown.platform.item.library.Cart;
//import brown.platform.item.library.Item;
//import brown.system.setup.ISetup;
//import brown.user.agent.IAgentBackend;
//import brown.user.agent.library.OnlineAgentBackend;
//
//public class LearningOfflineSubAgent extends OnlineAgentBackend implements IAgentBackend {
//
//  private IGeneralValuation agentValuation; 
//  private Map<String, List<Double>> biddingPrices; 
//  private Map<String, List<Double>> winningPrices; 
//  
//  
//  public LearningOfflineSubAgent(String host, int port, ISetup gameSetup) {
//    super(host, port, gameSetup); 
//    this.biddingPrices = new HashMap<String, List<Double>>(); 
//    this.winningPrices = new HashMap<String, List<Double>>(); 
//  }
//  
//  public LearningOfflineSubAgent(String host, int port, ISetup gameSetup, String name) {
//    super(host, port, gameSetup, name);
//    this.biddingPrices = new HashMap<String, List<Double>>(); 
//    this.winningPrices = new HashMap<String, List<Double>>(); 
//  }
//  
//  
//  @Override
//  public void onInformationMessage(IInformationMessage informationMessage) {
//    UserLogging.log("[+] Simulation Json File Name: " + this.simulationJsonFileName); 
//    UserLogging.log("[+] Information Message Received");
//    UserLogging.log(informationMessage); 
//  }
//
//  @Override
//  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
//    UserLogging.log("[+] Trade Request Message Received"); 
//    
//    Map<ICart, Double> bidMap = new HashMap<ICart, Double>(); 
//    List<IItem> bidItems = new LinkedList<IItem>(); 
//    
//    bidItems.add(new Item("testItem", 1)); 
//    
//    ICart bidCart = new Cart(bidItems); 
//    bidMap.put(bidCart, agentValuation.getValuation(bidCart)); 
//    IBidBundle oneSided = new OneSidedBidBundle(bidMap);
//    ITradeMessage tradeMessage = new TradeMessage(0, this.ID, tradeRequestMessage.getAuctionID(), oneSided);
//    UserLogging.log(tradeMessage); 
//    this.CLIENT.sendTCP(tradeMessage); 
//  }
//
//  @Override
//  public void onValuationMessage(IValuationMessage valuationMessage) {
//    UserLogging.log("[+] Valuation Message Received");
//    UserLogging.log(valuationMessage.toString()); 
//    this.agentValuation = valuationMessage.getValuation(); 
//  }
//
//  @Override
//  public void
//      onSimulationReportMessage(ISimulationReportMessage simReportMessage) {
//    // TODO: update learning data structure. 
//    UserLogging.log("THE AGENT WILL LEARN HERE."); 
//    System.out.println(simReportMessage); 
//    for (int key : simReportMessage.getMarketResults().keySet()) {
//      IMarketPublicState publicState = simReportMessage.getMarketResults().get(key); 
//      List<List<ITradeMessage>> tMessage = publicState.getTradeHistory(); 
//      for (List<ITradeMessage> tList : tMessage) {
//        for (ITradeMessage singleT : tList) {
//          Map<ICart, Double> carts = singleT.getBid().getBids(); 
//          for (ICart aCart : carts.keySet()) {
//            String itemName = aCart.getItems().get(0).getName(); 
//            if (this.biddingPrices.containsKey(itemName)) {
//              List<Double> prices = this.biddingPrices.get(itemName); 
//              prices.add(carts.get(aCart)); 
//              this.biddingPrices.put(itemName, prices); 
//            } else {
//              List<Double> prices = new LinkedList<Double>(); 
//              prices.add(carts.get(aCart)); 
//              this.biddingPrices.put(itemName, prices); 
//            }
//          }
//        }
//      }
//      List<IAccountUpdate> acctUpdates = publicState.getPayments(); 
//      for (IAccountUpdate update : acctUpdates) {
//        String itemName = update.getCart().getItems().get(0).getName(); 
//        double winningPrice = update.getCost(); 
//        if (this.winningPrices.containsKey(itemName)) {
//          List<Double> prices = this.winningPrices.get(itemName); 
//          prices.add(winningPrice); 
//          this.winningPrices.put(itemName, prices); 
//        } else {
//          List<Double> prices = new LinkedList<Double>(); 
//          prices.add(winningPrice); 
//          this.winningPrices.put(itemName, prices); 
//        }
//      }
//    }
//    System.out.println(this.biddingPrices);
//    System.out.println(this.winningPrices);
//    JSONObject joBidding = new JSONObject(this.biddingPrices); 
//    JSONObject joWinning = new JSONObject(this.winningPrices); 
//    
//    
//    FileWriter fw;
//    try {
//      fw = new FileWriter("bidding_prices.json");
//      fw.write(joBidding.toJSONString());
//      fw.close(); 
//    } catch (IOException e) {
//      e.printStackTrace();
//    } 
//    try {
//      fw = new FileWriter("winning_prices.json");
//      fw.write(joWinning.toJSONString());
//      fw.close(); 
//    } catch (IOException e) {
//      e.printStackTrace();
//    } 
//  }
//}