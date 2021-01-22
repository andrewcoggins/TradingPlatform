 package brown.user.agent.library;
//
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.Map;
//
// import org.json.simple.parser.ParseException;
//
// import brown.auction.value.valuation.IGeneralValuation;
// import brown.communication.bid.IBidBundle;
// import brown.communication.bid.library.OneSidedBidBundle;
// import brown.communication.messages.IBankUpdateMessage;
// import brown.communication.messages.IInformationMessage;
// import brown.communication.messages.ISimulationReportMessage;
// import brown.communication.messages.ITradeMessage;
// import brown.communication.messages.ITradeRequestMessage;
// import brown.communication.messages.IValuationMessage;
// import brown.communication.messages.library.TradeMessage;
// import brown.logging.library.UserLogging;
// import brown.platform.item.ICart;
// import brown.platform.item.IItem;
// import brown.platform.item.library.Cart;
// import brown.platform.item.library.Item;
// import brown.system.setup.ISetup;
// import brown.system.setup.library.Setup;
// import brown.user.agent.IAgentBackend;
//
/// **
// * But a smart agent learns.
// *
// * @author andrewcoggins
// *
// */
public class SimpleFPAgent { // extends AbsFictitiousPlayAgent implements
                             // IAgentBackend {
  //
  // private IGeneralValuation agentValuation;
  //
  // public SimpleFPAgent(String host, int port, ISetup gameSetup) {
  // super(host, port, gameSetup);
  // }
  //
  // public SimpleFPAgent(String host, int port, ISetup gameSetup, String name)
  // {
  // super(host, port, gameSetup, name);
  // }
  //
  // @Override
  // public void onInformationMessage(IInformationMessage informationMessage) {
  // UserLogging
  // .log("[+] Simulation Json File Name: " + this.simulationJsonFileName);
  // UserLogging.log("[+] Information Message Received");
  // UserLogging.log(informationMessage);
  // }
  //
  // @Override
  // public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage)
  // {
  // UserLogging.log("[+] Trade Request Message Received");
  //
  // Map<ICart, Double> bidMap = new HashMap<ICart, Double>();
  // List<IItem> bidItems = new LinkedList<IItem>();
  //
  // bidItems.add(new Item("testItem", 1));
  //
  // ICart bidCart = new Cart(bidItems);
  // bidMap.put(bidCart, agentValuation.getValuation(bidCart));
  // IBidBundle oneSided = new OneSidedBidBundle(bidMap);
  // ITradeMessage tradeMessage = new TradeMessage(0, this.ID,
  // tradeRequestMessage.getAuctionID(), oneSided);
  // UserLogging.log(tradeMessage);
  // this.CLIENT.sendTCP(tradeMessage);
  // }
  //
  // @Override
  // public void onValuationMessage(IValuationMessage valuationMessage) {
  // UserLogging.log("[+] Valuation Message Received");
  // UserLogging.log(valuationMessage.toString());
  // this.agentValuation = valuationMessage.getValuation();
  // System.out.println("starting FP");
  // this.initiateFictitiousPlay(valuationMessage, this.initialEndowment);
  // // TODO: read JSON output from learning agent.
  // }
  //
  // @Override
  // public void
  // onSimulationReportMessage(ISimulationReportMessage reportMessage) {
  // System.out.println(reportMessage);
  //
  // }
  //
  // private void initiateFictitiousPlay(IValuationMessage fictitiousValuation,
  // IBankUpdateMessage fictitiousEndowment) {
  // // initialize 'other agent' map
  // Map<String, String> allOtherAgents = new HashMap<String, String>();
  // // fill 'other agent' map.
  // allOtherAgents.put("FPAgentOne", "brown.user.agent.library.SimpleAgent");
  // double newSimulationDelayTime = 0.25;
  // int numLearningRuns = 2;
  // // generate threads for fictitious play.
  // Map<String, Runnable> FPRunnables = generateFPRunnables("meAgent",
  // "brown.user.agent.learningagent.library.LearningSubAgent",
  // fictitiousValuation, fictitiousEndowment, allOtherAgents,
  // newSimulationDelayTime, numLearningRuns);
  // // do fictitious play
  // doFictitiousPlay(FPRunnables);
  // }
  //
  // private void initiateFictitiousPlayOffline() {
  //
  // Map<String, String> allOtherAgents = new HashMap<String, String>();
  // allOtherAgents.put("FPAgentOne", "brown.user.agent.library.SimpleAgent");
  // double newSimulationDelayTime = 0.25;
  // int numLearningRuns = 100;
  // Map<String, Runnable> FPRunnables = generateFPThreadsOffline("meAgent",
  // "brown.user.agent.learningagent.library.LearningOfflineSubAgent",
  // allOtherAgents, newSimulationDelayTime, numLearningRuns);
  // // do fictitious play
  // doFictitiousPlay(FPRunnables);
  // }
  //
  // public static void main(String[] args) throws FileNotFoundException,
  // IOException, ParseException {
  // SimpleFPAgent fpAgent =
  // new SimpleFPAgent("localhost", 2121, new Setup(), "alice");
  // // uncomment to learn offline.
  //// fpAgent.simulationJsonFileName =
  // "input_configs/second_price_auction_fp.json";
  //// fpAgent.initiateFictitiousPlayOffline();
  ////
  // // TODO: read JSON output from out learning agent.
  //
  // while (true) {
  // }
  // }
  //
}