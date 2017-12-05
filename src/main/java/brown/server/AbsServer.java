package brown.server;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import brown.accounting.Account;
import brown.accounting.AccountManager;
import brown.accounting.Ledger;
import brown.accounting.MarketManager;
import brown.accounting.Order;
import brown.channels.server.IServerChannel;
import brown.channels.server.TwoSidedAuction;
import brown.market.IMarket;
import brown.market.library.Market;
import brown.messages.library.Ack;
import brown.messages.library.BankUpdate;
import brown.messages.library.Bid;
import brown.messages.library.GameReport;
import brown.messages.library.MarketOrder;
import brown.messages.library.NegotiateRequest;
import brown.messages.library.Registration;
import brown.messages.library.Trade;
import brown.messages.library.TradeRequest;
import brown.setup.Logging;
import brown.setup.ISetup;
import brown.setup.Startup;
import brown.tradeable.library.Tradeable;
import brown.value.config.AbsValueConfig;
import brown.value.valuationrepresentation.library.SimpleValuation;
import brown.value.valuationrepresentation.library.ValuationType;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * This is the server that all trading agent games will implement. It abstracts
 * away all of the communication logic and largely the shared market structures
 * (auctions, markets, trading) so that designers can focus on their game
 * specifics
 * 
 */
public abstract class AbsServer {
	protected Map<Connection, Integer> connections;
	protected Map<Integer, Integer> privateToPublic;
	// Consider time limiting these
	protected List<NegotiateRequest> pendingTradeRequests;
	protected AccountManager acctManager;
	protected MarketManager manager;

	private int agentCount;
	private final int PORT;
	protected Server theServer;
	protected boolean SHORT;
	
	protected AbsValueConfig valueConfig; 

	public AbsServer(int port, ISetup gameSetup) {
		this.PORT = port;
		this.agentCount = 0;
		this.connections = new ConcurrentHashMap<Connection, Integer>();
		this.privateToPublic = new ConcurrentHashMap<Integer, Integer>();
		this.acctManager = new AccountManager();
		this.pendingTradeRequests = new CopyOnWriteArrayList<NegotiateRequest>();
		this.manager = new MarketManager();
		this.privateToPublic.put(-1, -1);
		this.SHORT = false;

		theServer = new Server(8192, 4096);
		theServer.start();
		Kryo serverKryo = theServer.getKryo();
		Startup.start(serverKryo);
		if (gameSetup != null) {
			gameSetup.setup(serverKryo);
		}

		try {
			theServer.bind(PORT, PORT);
		} catch (IOException e) {
			Logging.log(e + " [X] Server failed to start due to port conflict");
			return;
		}

		final AbsServer aServer = this;
		theServer.addListener(new Listener() {
			public void received(Connection connection, Object message) {
				Integer id = null;
				if (connections.containsKey(connection)) {
					id = connections.get(connection);
				} else if (message instanceof Registration) {
					Logging.log("[-] registration recieved from "
							+ connection.getID());
					aServer.onRegistration(connection, (Registration) message);
					return;
				} else {
					return;
				}

				if (message instanceof Bid) {
					Logging.log("[-] bid recieved from " + id);
					aServer.onBid(connection, id, (Bid) message);
				} else if (message instanceof NegotiateRequest) {
					Logging.log("[-] traderequest recieved from " + id);
					aServer.onTradeRequest(connection, id,
							(NegotiateRequest) message);
				} else if (message instanceof Trade) {
					Logging.log("[-] trade recieved from " + id);
					aServer.onTrade(connection, (Trade) message);
				} else if (message instanceof MarketOrder) {
					Logging.log("[-] limitorder recieved from " + id);
					Logging.log("ERROR: Limit order functionality not present");
					//aServer.onLimitOrder(connection, id, (MarketOrder) message);
				}
			}
		});
		Logging.log("[-] server started");
	}


	/*
	 * This method is invoked when a new agent connects to the game
	 * 
	 * @param connection - details of their connection to the server
	 * 
	 * @param registration - details of their game logic agent status
	 */
	//TODO: edit for valuatoin.
	protected void onRegistration(Connection connection,
			Registration registration) {
		Integer theID = this.defaultRegistration(connection, registration);
		if (theID == null) {
			// TODO: add rejection
			return;
		}
		theServer.sendToTCP(connection.getID(), new Registration(theID));
	}

	/*
	 * The server receives trade requests and forwards them to the correct
	 * agent(s)
	 * 
	 * @param connection - agent connection info
	 * 
	 * @param privateID - (safe) privateID of the requesting agent
	 * 
	 * @param tradeRequest - the trade request
	 */
	protected void onTradeRequest(Connection connection, Integer privateID,
			NegotiateRequest tradeRequest) {
		NegotiateRequest tr = tradeRequest.safeCopy(privateToPublic
				.get(privateID));
		if (privateID != -1) {
			theServer.sendToAllTCP(tr);
		} else {
			theServer.sendToTCP(connection.getID(), tr);
		}
		pendingTradeRequests.add(tr);
	}

	/*
	 * What happens when a trade request is accepted or rejected
	 * 
	 * @param connection - agent connection info
	 * 
	 * @param trade - tuple of trade request and acceptance boolean
	 */
	protected void onTrade(Connection connection, Trade trade) {
		Integer privateTo = connections.get(connection);
		Integer privateFrom = publicToPrivate(trade.tradeRequest.fromID);
		if (privateFrom == null) {
			return;
		}
		if (pendingTradeRequests.contains(trade.tradeRequest)) {
			if (!trade.accept) {
				if (privateToPublic.get(privateTo) == trade.tradeRequest.fromID
						|| privateToPublic.get(privateTo) == trade.tradeRequest.toID) {
					pendingTradeRequests.remove(trade.tradeRequest);
				}
			} else if (privateToPublic.get(privateTo) == trade.tradeRequest.toID
					|| trade.tradeRequest.toID == -1) {
				Account toAccount = acctManager.getAccount(privateTo);
				Account fromAccount = acctManager.getAccount(privateFrom);

				if (trade.tradeRequest.isSatisfied(toAccount, fromAccount)) {
					Account middleTo = toAccount.remove(
							trade.tradeRequest.moniesRequested,
							trade.tradeRequest.sharesRequested);
					Account newTo = middleTo.addAll(
							trade.tradeRequest.moniesOffered,
							trade.tradeRequest.sharesOffered);

					Account middleFrom = fromAccount.remove(
							trade.tradeRequest.moniesOffered,
							trade.tradeRequest.sharesOffered);
					Account newFrom = middleFrom.addAll(
							trade.tradeRequest.moniesRequested,
							trade.tradeRequest.sharesRequested);

					acctManager.setAccount(privateTo, newTo);
					acctManager.setAccount(privateFrom, newFrom);

					List<Integer> ids = new LinkedList<Integer>();
					//take a look at locking schemes
					ids.add(privateTo);
					ids.add(privateFrom);
					sendBankUpdates(ids);
				}
			}
		}
	}

	/*
	 * This will handle what happens when an agent sends in a bid in response to
	 * a BidRequest for an auction
	 */
	protected void onBid(Connection connection, Integer privateID, Bid bid) {
		Market auction = this.manager.getIMarket(bid.AuctionID);
		if (auction != null) {
			synchronized (auction) {
				Account account = this.acctManager.getAccount(privateID);
				if (!auction.handleBid(bid.safeCopy(privateID))
				    || (!this.SHORT && account.monies < bid.Bundle.getCost())) {
					Ack rej = new Ack(privateID, bid, true);
					this.theServer.sendToTCP(connection.getID(), rej);
				}
			}
		} else {
			Ack rej = new Ack(privateID, bid, true);
			this.theServer.sendToTCP(connection.getID(), rej);
		}
	}

	/*
	 * Sends a bank update to a set of agents
	 * 
	 * @param List<Integer> set of IDs to send to
	 */
	public void sendBankUpdates(List<Integer> IDs) {
		synchronized (IDs) {
			for (Integer ID : IDs) {
				Connection connection = privateToConnection(ID);
				if (connection == null) {
					continue;
				}
				Account account = this.acctManager.getAccount(ID);
				if (account == null) {
					continue;
				}
				BankUpdate bu = new BankUpdate(ID, null, account.toAgent());
				theServer.sendToTCP(connection.getID(), bu);
			}
		}
	}

	/*
	 * Sends a market update to every agent about the state of all the public
	 * markets
	 * 
	 * NOTE: No need for sync since this is access only
	 */
	public void sendAllMarketUpdates(List<TwoSidedAuction> tsas) {
		int i = 0;
		for (TwoSidedAuction sec : tsas) {
			TradeRequest mupdate = new TradeRequest(i++, sec.wrap(this.manager
					.getLedger(sec.getID()).getSanitized(null)),
					sec.getMechanismType());
			theServer.sendToAllTCP(mupdate);
			this.manager.getLedger(sec.getID()).clearLatest();
		}
	}

	/**
	 * Sends a auction update to every agent or closes out any finished
	 * auctions about the state of all the public auctions
	 */
	public void updateAllAuctions(boolean closeable) {
		synchronized (this.manager) {;
			List<Market> toRemove = new LinkedList<Market>();
			for (Market auction : this.manager.getAuctions()) {
				synchronized (auction) {
					auction.tick(System.currentTimeMillis());
					if (auction.isOver() && closeable) {
						toRemove.add(auction);
						List<Order> winners = auction.getOrders();
						System.out.println(winners.size());
						if (winners == null) {
							continue;
						}
						Ledger ledger = this.manager.getLedger(auction.getID());
						for (Order winner : winners) {
							if (winner.TO != null && this.
									acctManager.containsAcct(winner.TO)) {
								Account accountTo = this.acctManager.
										getAccount(winner.TO);
								synchronized (accountTo.ID) {
									//winner.GOOD.setAgentID(winner.TO);
									ledger.add(winner.toTransaction());
									
									Account newA = accountTo.add(
											-1 * winner.COST,
											winner.GOOD);
									this.acctManager.setAccount(winner.TO, newA);
									this.sendBankUpdate(winner.TO, accountTo,
											newA);
								}
							}
							if (winner.FROM != null && this.acctManager
									.containsAcct(winner.FROM)) {
								Account accountFrom = this.acctManager
										.getAccount(winner.FROM);
								synchronized (accountFrom.ID) {									
									Account newA = accountFrom.remove(
											-1 * winner.COST,
											winner.GOOD);
									this.acctManager.setAccount(winner.FROM, newA);
									System.out.println("reached"); 
									this.sendBankUpdate(winner.FROM, accountFrom,
											newA);
								}
							}
						}
						auction.clearState();
					} else {
						for (Map.Entry<Connection, Integer> id : this.connections
								.entrySet()) {
						  //maybe the trade request can be a ledger that's only one trade deep.
						  //before, this sent a blank ledger. 
						  //trying to send a blank one if it's the first round, or if the allocation rules are null.
	            System.out.println("A");
							TradeRequest tr = auction.constructTradeRequest(id.getValue());
									//this.manager.getLedger(auction.getID())
									//		.getSanitized(id.getValue()));//TODO: Fix
							if (tr == null) {
							  
								continue;
							}
							this.theServer.sendToUDP(id.getKey().getID(), tr);
						}
						//this.manager.getLedger(auction.getID()).clearLatest();
					}
				}
			}

			for (Market auction : toRemove) {
				GameReport report = auction.getReport();
				if (report != null) {
					this.theServer.sendToAllTCP(report);
				}
				this.manager.close(this, auction.getID(), null);
			}
		}
	}
	
	/*
	 * This loops an inner cycle of an auction. For instance, If one round of an ascending auction.
	 * TODO: synchronize.
	 * TODO: update auction. 
	 */
	//not in use.
	public void innerCycle(Integer marketID, Integer agentID) { 
	  IMarket market = this.manager.getIMarket(marketID);
	  market.tick((long) 1.0); 
	  if (!market.isOver()) {
	    //update the ledger?
	    TradeRequest tradeReq = market.constructTradeRequest(agentID);
	    this.theServer.sendToUDP(agentID, tradeReq);
	  } else {
	    //update the bank, send bank update.
      List<Order> winners = market.getOrders();	    
      Ledger ledger = this.manager.getLedger(market.getID());
      for (Order winner : winners) {
        if (winner.TO != null && this.
            acctManager.containsAcct(winner.TO)) {
          Account accountTo = this.acctManager.getAccount(winner.TO);
          synchronized (accountTo.ID) {
            //winner.GOOD.setAgentID(winner.TO);
            ledger.add(winner.toTransaction()); 
            Account newA = accountTo.add(-1 * winner.COST, winner.GOOD);
            this.acctManager.setAccount(winner.TO, newA);
            this.sendBankUpdate(winner.TO, accountTo, newA);
          }
        }
        if (winner.FROM != null && this.acctManager.containsAcct(winner.FROM)) {
          Account accountFrom = this.acctManager.getAccount(winner.FROM);
          synchronized (accountFrom.ID) {                 
            Account newA = accountFrom.remove(-1 * winner.COST, winner.GOOD);
            this.acctManager.setAccount(winner.FROM, newA);
            this.sendBankUpdate(winner.FROM, accountFrom,
                newA);
          }
        }
      }
	  }
	}
	
	//not currently in use.
	public synchronized void outerCycle(Integer marketID, Integer agentID) {
	  //run every inner cycle of the auction until it is terminated per the inner termination condition.
	  Market market = this.manager.getIMarket(marketID); 
	  while (!market.isOver()) { 
	    this.innerCycle(marketID, agentID);
	  }
	  this.innerCycle(marketID, agentID);
	}
	
	public synchronized void completeAuction(Integer marketID) throws InterruptedException { 
	  //run every outer cycle of the auction until it is terminated per the outer termination condition.
	  Market market = this.manager.getIMarket(marketID); 
	 while(!market.isOverOuter()) {
	   while(!market.isOver()) {
       Thread.sleep(1000);
	     this.updateAllAuctions(true);
	     Thread.sleep(1000);
	   }
	   this.updateAllAuctions(true);
     Thread.sleep(1000);
	 }
	}

	/*
	 * Sends a MarketUpdate about this specific market to all agents
	 * 
	 * @param Security : the market to update on
	 */
	public void sendMarketUpdate(IServerChannel market) {
//		synchronized(market) {
//			for (Entry<Connection, Integer> ID : this.connections.entrySet()) {
//				TradeRequest mupdate = new TradeRequest(0, market.wrap(this.manager
//						.getLedger(market.getID()).getSanitized(ID.getValue())),
//						market.getMechanismType());
//				System.out.println("SEND MARKET UPDATES IS BEING USED");
//				theServer.sendToTCP(ID.getKey().getID(), mupdate);
//			}
//			this.manager.getLedger(market.getID()).clearLatest();
//		}
    System.out.println("SEND MARKET UPDATES IS BEING USED");
	}

	/*
	 * Agents only know each other's public IDs. Private IDs are only known to
	 * the agents themselves and are needed to authorize any actions. The server
	 * refers to agents by their private IDs at all times.
	 */
	protected Integer publicToPrivate(Integer id) {
		for (Entry<Integer, Integer> ptp : privateToPublic.entrySet()) {
			if (ptp.getValue() == id) {
				return ptp.getKey();
			}
		}

		return null;
	}

	/*
	 * Retrieves a connection (needed to send a message to a client) from the
	 * agent's private ID
	 */
	protected Connection privateToConnection(Integer id) {
		if (id == null) {
			return null;
		}

		for (Entry<Connection, Integer> ctp : connections.entrySet()) {
			if (ctp.getValue().intValue() == id.intValue()) {
				return ctp.getKey();
			}
		}

		return null;
	}

	/*
	 * Retrieves an agent's bank account from its public ID
	 */
	public Account publicToAccount(Integer id) {
		return this.acctManager.getAccount(publicToPrivate(id));
	}

	/*
	 * Retrieves an agent's bank account from its private ID
	 */
	public Account privateToAccount(Integer id) {
		return this.acctManager.getAccount(id);
	}



	/*
	 * Sends a bank update to a set of agents
	 * 
	 * @param List<Integer> set of IDs to send to
	 */
	public void sendBankUpdates(Set<Integer> IDs) {
		synchronized (IDs) {
			for (Integer ID : IDs) {
				Connection connection = privateToConnection(ID);
				if (connection == null) {
					continue;
				}
				BankUpdate bu = new BankUpdate(ID, null, this.acctManager.getAccount(ID));
				theServer.sendToTCP(connection.getID(), bu);
			}
		}
	}

	/**
	 * Singular bank update
	 * 
	 * @param ID
	 * @param oldA
	 * @param newA
	 */
	public void sendBankUpdate(Integer ID, Account oldA, Account newA) {
		BankUpdate bu = new BankUpdate(ID, oldA.toAgent(), newA.toAgent());
		theServer.sendToTCP(this.privateToConnection(ID).getID(), bu);
	}

	/**
	 * Default registration; allows modified reg message
	 * 
	 * @param connection
	 *            : new connection
	 * @param registration
	 *            : new registration
	 * @return safe privateID mapped to connection
	 */
	public Integer defaultRegistration(Connection connection,
			Registration registration) {
		if (registration.getID() == null) {
			return null;
		}

		this.theServer.sendToTCP(connection.getID(), new Ack(registration,
				false));

		Collection<Integer> allIds = connections.values();
		Integer theID = registration.getID();
		if (allIds.contains(theID)) {
			Connection oldConnection = null;
			for (Connection c : connections.keySet()) {
				if (connections.get(c).equals(theID)) {
					oldConnection = c;
					if (!oldConnection.getRemoteAddressTCP().equals(
							connection.getRemoteAddressTCP())) {
						return null;
					}
					break;
				}
			}
			connections.remove(oldConnection);

			return null;
		} else {
			theID = new Integer((int) (Math.random() * 1000000000));
			while (allIds.contains(theID)) {
				theID = new Integer((int) (Math.random() * 1000000000));
			}

			privateToPublic.put(theID, agentCount++);
			this.acctManager.setAccount(theID, new Account(theID));

			connections.put(connection, theID);
			Logging.log("[-] registered " + theID);

			return theID;
		}
	}
	
	/**
	 * Hack, for MarketManager
	 */
	
	public void setAccount(Integer ID, Account account) {
		acctManager.setAccount(ID, account);
	}

}
