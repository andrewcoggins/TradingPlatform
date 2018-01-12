package brown.server;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
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
import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.BidMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.MarketOrderMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.Logging;
import brown.setup.ISetup;
import brown.setup.Startup;
import brown.value.config.AbsValueConfig;
import brown.value.valuation.library.AdditiveValuation;
import brown.value.valuation.library.BundleValuation;
import brown.value.valuationrepresentation.AbsValuationRepresentation;
import brown.value.valuationrepresentation.library.ComplexValuation;
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
	protected List<NegotiateRequestMessage> pendingTradeRequests;
	protected AccountManager acctManager;
	protected MarketManager manager;

	private int agentCount;
	private final int PORT;
	protected Server theServer;
	protected boolean SHORT;
	
	protected Map<Integer, AbsValueConfig> valueConfig; 
	// a map from an agents' private id to its private valuation for goods.
	// what if there are different sets of goods? 
	// valuation manager?
	//each game on the simul axis has a map from integer to private valuation.
	//does the server even care what the private valuations are? 
	private Map<Integer, Map<Integer, AbsValuationRepresentation>> privateValuations;

	public AbsServer(int port, ISetup gameSetup) {
		this.PORT = port;
		this.agentCount = 0;
		this.connections = new ConcurrentHashMap<Connection, Integer>();
		this.privateToPublic = new ConcurrentHashMap<Integer, Integer>();
		this.acctManager = new AccountManager();
		this.pendingTradeRequests = new CopyOnWriteArrayList<NegotiateRequestMessage>();
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
				} else if (message instanceof RegistrationMessage) {
					Logging.log("[-] registration recieved from "
							+ connection.getID());
					aServer.onRegistration(connection, (RegistrationMessage) message);
					return;
				} else {
					return;
				}

				if (message instanceof BidMessage) {
					Logging.log("[-] bid recieved from " + id);
					aServer.onBid(connection, id, (BidMessage) message);
				} else if (message instanceof NegotiateRequestMessage) {
					Logging.log("[-] traderequest recieved from " + id);
					aServer.onTradeRequest(connection, id,
							(NegotiateRequestMessage) message);
				} else if (message instanceof TradeMessage) {
					Logging.log("[-] trade recieved from " + id);
					aServer.onTrade(connection, (TradeMessage) message);
				} else if (message instanceof MarketOrderMessage) {
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
	//TODO: how do we determine the exact valuation for the complex case? 
	//TODO: eventually get out of this enum crisis.
	//TODO: abstract valuable that can be virtually anything.
	//TODO: What about initial conditions and states of the world?
	//TODO: need a data structure storing each agents' private valuation, needed to calculate utility.
	//TODO: provide some file for logging winners, etc.
	protected void onRegistration(Connection connection,
			RegistrationMessage registration) {
    System.out.println("onRegistration called");
    System.out.println(this.valueConfig.keySet().size());
		Integer agentID = this.defaultRegistration(connection, registration);
    if (agentID == null) {
      // TODO: add rejection
      System.out.println("Agent ID null");
      return;
    }
		for(Integer marketNum : this.valueConfig.keySet()) {
		  AbsValueConfig marketConfig = this.valueConfig.get(marketNum);
		  ValuationRegistrationMessage valueReg; 
		  //simple valuations: the agent gets a valuation over single goods.
		  if (marketConfig.valueScheme == ValuationType.Simple) {
		    System.out.println("Got here");
		    AdditiveValuation simpleVal = 
		        new AdditiveValuation(marketConfig.aGenerator, marketConfig.allGoods);
		    SimpleValuation privateVal = simpleVal.getValuation(marketConfig.allGoods);
		    valueReg = new ValuationRegistrationMessage(agentID, privateVal, simpleVal);
		    theServer.sendToTCP(connection.getID(), valueReg);
		  } else if (marketConfig.valueScheme == ValuationType.Complex) {
		    //complex valuations: the agent gets a valuation over complex goods.
		    BundleValuation complexVal = 
		        new BundleValuation(marketConfig.aGenerator, true, marketConfig.allGoods);
		        ComplexValuation privateVal = complexVal.getValuation(marketConfig.allGoods);
		        valueReg = new ValuationRegistrationMessage(agentID, privateVal,complexVal);
		        theServer.sendToTCP(connection.getID(), valueReg);
		  } else {
		    //no explicit valuation, as in the lemonade game
		    theServer.sendToTCP(connection.getID(), new RegistrationMessage(agentID));
		  }
		}
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
			NegotiateRequestMessage tradeRequest) {
		NegotiateRequestMessage tr = tradeRequest.safeCopy(privateToPublic
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
	protected void onTrade(Connection connection, TradeMessage trade) {
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
          toAccount.remove(
              trade.tradeRequest.moniesRequested,
              trade.tradeRequest.sharesRequested);
				  toAccount.add(
              trade.tradeRequest.moniesOffered,
              trade.tradeRequest.sharesOffered);
	        fromAccount.remove(
	             trade.tradeRequest.moniesOffered,
	             trade.tradeRequest.sharesOffered); 
				  fromAccount.add(
				     trade.tradeRequest.moniesRequested,
             trade.tradeRequest.sharesRequested);
					acctManager.setAccount(privateTo, toAccount);
					acctManager.setAccount(privateFrom, fromAccount);

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
	protected void onBid(Connection connection, Integer privateID, BidMessage bid) {
		Market auction = this.manager.getIMarket(bid.AuctionID);
		if (auction != null) {
			synchronized (auction) {
				Account account = this.acctManager.getAccount(privateID);
				if (!auction.handleBid(bid.safeCopy(privateID))
				    || (!this.SHORT && account.getMonies() < bid.Bundle.getCost())) {
					AckMessage rej = new AckMessage(privateID, bid, true);
					this.theServer.sendToTCP(connection.getID(), rej);
				}
			}
		} else {
			AckMessage rej = new AckMessage(privateID, bid, true);
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
				BankUpdateMessage bu = new BankUpdateMessage(ID, null, account.copyAccount());
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
			TradeRequestMessage mupdate = new TradeRequestMessage(i++, sec.wrap(this.manager
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
									
									accountTo.add(-1 * winner.COST, winner.GOOD);
									this.acctManager.setAccount(winner.TO, accountTo);
									this.sendBankUpdate(winner.TO, accountTo, accountTo);
								}
							}
							if (winner.FROM != null && this.acctManager
									.containsAcct(winner.FROM)) {
								Account accountFrom = this.acctManager
										.getAccount(winner.FROM);
								synchronized (accountFrom.ID) {									
									accountFrom.remove(-1 * winner.COST, winner.GOOD);
									this.acctManager.setAccount(winner.FROM, accountFrom);
									System.out.println("reached"); 
									this.sendBankUpdate(winner.FROM, accountFrom, accountFrom);
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
							TradeRequestMessage tr = auction.constructTradeRequest(id.getValue());
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
				GameReportMessage report = auction.getReport();
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
	    TradeRequestMessage tradeReq = market.constructTradeRequest(agentID);
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
            accountTo.add(-1 * winner.COST, winner.GOOD);
            this.acctManager.setAccount(winner.TO, accountTo);
            this.sendBankUpdate(winner.TO, accountTo, accountTo);
          }
        }
        if (winner.FROM != null && this.acctManager.containsAcct(winner.FROM)) {
          Account accountFrom = this.acctManager.getAccount(winner.FROM);
          synchronized (accountFrom.ID) {                 
            accountFrom.remove(-1 * winner.COST, winner.GOOD);
            this.acctManager.setAccount(winner.FROM, accountFrom);
            this.sendBankUpdate(winner.FROM, accountFrom,
                accountFrom);
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
				BankUpdateMessage bu = new BankUpdateMessage(ID, null, this.acctManager.getAccount(ID));
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
		BankUpdateMessage bu = new BankUpdateMessage(ID, oldA.copyAccount(), newA.copyAccount());
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
			RegistrationMessage registration) {
		if (registration.getID() == null) {
			return null;
		}

		this.theServer.sendToTCP(connection.getID(), new AckMessage(registration,
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
