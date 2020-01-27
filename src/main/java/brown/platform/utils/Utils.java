package brown.platform.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.marketstate.library.MarketPublicState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.InformationMessage;
import brown.communication.messages.library.SimulationReportMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.accounting.library.AccountUpdate;

public class Utils {

  public static IServerToAgentMessage sanitize(IServerToAgentMessage message,
      Map<Integer, Integer> agentIDs) {
    // sanitize the private IDs from an information message
    if (message instanceof IInformationMessage) {

      IInformationMessage iMessage = (IInformationMessage) message;
      IMarketPublicState publicState = iMessage.getPublicState();

      IMarketPublicState newPublicState = sanitizeState(publicState, agentIDs);

      IInformationMessage newMessage = new InformationMessage(
          iMessage.getMessageID(), iMessage.getAgentID(), newPublicState);

      return newMessage;
    } else if (message instanceof ISimulationReportMessage) {
      ISimulationReportMessage oldMessage = (ISimulationReportMessage) message;
      Map<Integer, IMarketPublicState> reports = oldMessage.getMarketResults();
      Map<Integer, IMarketPublicState> newReports =
          new HashMap<Integer, IMarketPublicState>();

      for (Integer marketID : reports.keySet()) {
        IMarketPublicState publicState = reports.get(marketID);
        IMarketPublicState newPublicState =
            sanitizeState(publicState, agentIDs);
        newReports.put(marketID, newPublicState);
      }
      ISimulationReportMessage newMessage = new SimulationReportMessage(
          oldMessage.getMessageID(), oldMessage.getAgentID(), newReports);
      return newMessage;
    } else if (message instanceof ITradeRequestMessage) {
      return message;
    }

    return message;
  }

  private static IMarketPublicState sanitizeState(
      IMarketPublicState publicState, Map<Integer, Integer> agentIDs) {

    // TODO: take this boy apart and put its pieces back together after
    // changing out all the IDs.

    List<List<ITradeMessage>> tradeHistory = publicState.getTradeHistory();
    List<IAccountUpdate> payments = publicState.getPayments();
    Map<String, Double> reserves = publicState.getReserves();
    int ticks = publicState.getTicks();
    long time = publicState.getTime();
    IMarketPublicState newPublicState = new MarketPublicState();

    // ticks
    newPublicState.setTicks(ticks);
    // time
    newPublicState.setTime(time);
    // reserves
    newPublicState.setReserves(reserves);
    // replace trade history
    for (List<ITradeMessage> trades : tradeHistory) {
      List<ITradeMessage> newTrades = new LinkedList<ITradeMessage>();
      for (ITradeMessage trade : trades) {
        Integer publicAgentID = agentIDs.get(trade.getAgentID());

        ITradeMessage newTradeMessage = new TradeMessage(trade.getMessageID(),
            publicAgentID, trade.getAuctionID(), trade.getBid());
        newTrades.add(newTradeMessage);
      }
      newPublicState.addToTradeHistory(newTrades);
    }

    // replace account updates
    List<IAccountUpdate> newAccountUpdates = new LinkedList<IAccountUpdate>();

    for (IAccountUpdate acctUpdate : payments) {
      Integer to = acctUpdate.getTo();
      Integer from = acctUpdate.getFrom();
      if (to > 0)
        to = agentIDs.get(to);
      if (from > 0)
        from = agentIDs.get(from);
      newAccountUpdates.add(new AccountUpdate(to, from, acctUpdate.getCost(),
          acctUpdate.getCart()));
    }
    newPublicState.setPayments(newAccountUpdates);

    return newPublicState;
  }

  public static IMarketPublicState toPublicState(IMarketState state) {

    IMarketPublicState newState = new MarketPublicState();

    newState.setTicks(state.getTicks());
    newState.setTime(state.getTime());
    newState.setAllocation(state.getAllocation());
    newState.setPayments(state.getPayments());

    for (List<ITradeMessage> tradeStep : state.getTradeHistory())
      newState.addToTradeHistory(tradeStep);

    return newState;
  }

}
