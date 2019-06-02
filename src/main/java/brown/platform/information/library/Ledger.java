package brown.platform.information.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.communication.messages.ITradeMessage;
import brown.platform.accounting.ITransaction;
import brown.platform.information.ILedger;

public class Ledger implements ILedger {

  private Map<Integer, Map<Integer, List<ITradeMessage>>> acceptedBids;
  private Map<Integer, Map<Integer, List<ITradeMessage>>> rejectedBids;
  private Map<Integer, List<ITransaction>> transactions;

  public Ledger() {
    this.acceptedBids = new HashMap<Integer, Map<Integer, List<ITradeMessage>>>();
    this.rejectedBids = new HashMap<Integer, Map<Integer, List<ITradeMessage>>>();
    this.transactions = new HashMap<Integer, List<ITransaction>>();
  }

  @Override
  public void postBid(ITradeMessage aBid, Integer timeStep) {
    if (this.acceptedBids.containsKey(aBid.getAuctionID())) {
      Map<Integer, List<ITradeMessage>> marketBids = this.acceptedBids.get(aBid.getAuctionID()); 
      if (marketBids.containsKey(timeStep)) {
        List<ITradeMessage> bids = marketBids.get(timeStep); 
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      } else {
        List<ITradeMessage> bids = new LinkedList<ITradeMessage>();
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      }
      this.acceptedBids.put(aBid.getAuctionID(), marketBids); 
    } else {
      Map<Integer, List<ITradeMessage>> marketBids = new HashMap<Integer, List<ITradeMessage>>(); 
      List<ITradeMessage> bids = new LinkedList<ITradeMessage>(); 
      bids.add(aBid); 
      marketBids.put(timeStep, bids); 
      this.acceptedBids.put(aBid.getAuctionID(), marketBids); 
    }
  }

  @Override
  public void postRejectedBid(ITradeMessage aBid, Integer timeStep) {
    if (this.rejectedBids.containsKey(aBid.getAuctionID())) {
      Map<Integer, List<ITradeMessage>> marketBids = this.rejectedBids.get(aBid.getAuctionID()); 
      if (marketBids.containsKey(timeStep)) {
        List<ITradeMessage> bids = marketBids.get(timeStep); 
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      } else {
        List<ITradeMessage> bids = new LinkedList<ITradeMessage>();
        bids.add(aBid); 
        marketBids.put(timeStep, bids); 
      }
      this.rejectedBids.put(aBid.getAuctionID(), marketBids); 
    } else {
      Map<Integer, List<ITradeMessage>> marketBids = new HashMap<Integer, List<ITradeMessage>>(); 
      List<ITradeMessage> bids = new LinkedList<ITradeMessage>(); 
      bids.add(aBid); 
      marketBids.put(timeStep, bids); 
      this.rejectedBids.put(aBid.getAuctionID(), marketBids); 
    }
  }

  @Override
  public void postTransaction(Integer marketID, ITransaction transaction) {
    if (this.transactions.containsKey(marketID)) {
      List<ITransaction> marketTransactions = this.transactions.get(marketID);
      marketTransactions.add(transaction); 
      this.transactions.put(marketID, marketTransactions);
    } else {
      List<ITransaction> marketTransactions = new LinkedList<ITransaction>(); 
      marketTransactions.add(transaction); 
      this.transactions.put(marketID, marketTransactions);
    }
  }

  @Override
  public Map<Integer, List<ITradeMessage>> getAcceptedBids(Integer marketID) {
    if (this.acceptedBids.containsKey(marketID)) {
      return this.acceptedBids.get(marketID);     
    } else {
      return new HashMap<Integer, List<ITradeMessage>>(); 
    }
  }

  @Override
  public Map<Integer, List<ITradeMessage>> getRejectedBids(Integer marketID) {
    if (this.rejectedBids.containsKey(marketID)) {
      return this.rejectedBids.get(marketID);     
    } else {
      return new HashMap<Integer, List<ITradeMessage>>(); 
    }
  }

  @Override
  public List<ITransaction> getMarketTransactions(Integer marketID) {
    if (this.transactions.containsKey(marketID)) {
      return this.transactions.get(marketID); 
    } else {
      return new LinkedList<ITransaction>(); 
    }
  }

}
