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

import brown.accounting.library.Account;
import brown.accounting.library.AccountManager;
import brown.accounting.library.Ledger;
import brown.channels.server.ITwoSidedAuctionChannel;
import brown.channels.server.TwoSidedAuction;
import brown.market.IMarket;
import brown.market.library.Market;
import brown.market.library.MarketManager;
import brown.market.marketstate.library.Order;
import brown.messages.library.AckMessage;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.TradeMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.MarketOrderMessage;
import brown.messages.library.NegotiateRequestMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.NegotiateMessage;
import brown.messages.library.TradeRequestMessage;
import brown.messages.library.ValuationRegistrationMessage;
import brown.setup.Logging;
import brown.setup.library.Startup;
import brown.setup.ISetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.ValConfig;
import brown.value.valuation.IValuation;
import brown.value.valuation.ValuationType;
import brown.value.valuation.library.AdditiveValuation;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * This is the server that all trading agent games will implement. It abstracts
 * away all of the communication logic and largely the shared market structures
 * (auctions, markets, trading) so that designers can focus on their game specifics
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
	
	protected Map<Integer, ValConfig> valueConfig; 
	protected List<ITradeable> initialGoods;
	protected Double initialMonies;

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

				if (message instanceof TradeMessage) {
					Logging.log("[-] bid recieved from " + id);
					aServer.onBid(connection, id, (TradeMessage) message);
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
	//TODO: What about initial conditions and states of the world?
	//TODO: need a data structure storing each agents' private valuation, needed to calculate utility.
	//TODO: provide some file for logging winners, etc.
	protected void onRegistration(Connection connection,
			RegistrationMessage registration) {
		Integer agentID = this.defaultRegistration(connection, registration);
    if (agentID == null) {
      // TODO: add rejection
      return;
    }
		for(Integer marketNum : this.valueConfig.keySet()) {
		  ValConfig marketConfig = this.valueConfig.get(marketNum);
		  if (marketConfig.type == ValuationType.Auction) {
	      ValuationRegistrationMessage valueReg; 
	      IValuation privateValuation = marketConfig.valueDistribution.sample();
	      valueReg = new ValuationRegistrationMessage(agentID, privateValuation, marketConfig.valueDistribution);
	      theServer.sendToTCP(connection.getID(), valueReg);
		  } else if (marketConfig.type == ValuationType.Game) {
	      //no explicit valuation, as in the lemonade game
	      theServer.sendToTCP(connection.getID(), new RegistrationMessage(agentID));
		  }
		}
	}


	/*
	 * This will handle what happens when an agent sends in a bid in response to
	 * a BidRequest for an auction
	 */
	protected void onBid(Connection connection, Integer privateID, TradeMessage bid) {
		Market auction = this.manager.getMarket(bid.AuctionID);
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
					.getLedger(sec.getID()).getSanitizedUnshared(null)),
					sec.getMechanismType());
			theServer.sendToAllTCP(mupdate);
			this.manager.getLedger(sec.getID()).clearUnshared();
		}
	}

	/**
	 * Sends a auction update to every agent or closes out any finished
	 * auctions about the state of all the public auctions
	 */
	public void updateAllAuctions(boolean closeable) {
		synchronized (this.manager) {;
			for (Market auction : this.manager.getAuctions()) {
				synchronized (auction) {				  
					auction.tick(System.currentTimeMillis());
					if (auction.isInnerOver() && closeable) {
						List<Order> winners = auction.constructOrders();
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
									
									accountTo.add(-1 * winner.PRICE, winner.GOOD);
									this.acctManager.setAccount(winner.TO, accountTo);
									this.sendBankUpdate(winner.TO, accountTo, accountTo);
								}
							}
							if (winner.FROM != null && this.acctManager
									.containsAcct(winner.FROM)) {
								Account accountFrom = this.acctManager
										.getAccount(winner.FROM);
								synchronized (accountFrom.ID) {									
									accountFrom.remove(-1 * winner.PRICE, winner.GOOD);
									this.acctManager.setAccount(winner.FROM, accountFrom);
									System.out.println("reached"); 
									this.sendBankUpdate(winner.FROM, accountFrom, accountFrom);
								}
							}
						}
		        this.theServer.sendToAllTCP(auction.constructReport());
	          if (!auction.isOverOuter()){
	            Logging.log("[*] Auction has been reset");
	            auction.resetInnerMarket();              
	          }         
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
		}
	}
	
	/*
	 * This loops an inner cycle of an auction. For instance, If one round of an ascending auction.
	 * TODO: synchronize.
	 * TODO: update auction. 
	 */
	//not in use.
	public void innerCycle(Integer marketID, Integer agentID) { 
	  IMarket market = this.manager.getMarket(marketID);
	  market.tick((long) 1.0); 
	  if (!market.isInnerOver()) {
	    //update the ledger?
	    TradeRequestMessage tradeReq = market.constructTradeRequest(agentID);
	    this.theServer.sendToUDP(agentID, tradeReq);
	  } else {
	    //update the bank, send bank update.
      List<Order> winners = market.constructOrders();	    
      Ledger ledger = this.manager.getLedger(market.getID());
      for (Order winner : winners) {
        if (winner.TO != null && this.
            acctManager.containsAcct(winner.TO)) {
          Account accountTo = this.acctManager.getAccount(winner.TO);
          synchronized (accountTo.ID) {
            //winner.GOOD.setAgentID(winner.TO);
            ledger.add(winner.toTransaction()); 
            accountTo.add(-1 * winner.PRICE, winner.GOOD);
            this.acctManager.setAccount(winner.TO, accountTo);
            this.sendBankUpdate(winner.TO, accountTo, accountTo);
          }
        }
        if (winner.FROM != null && this.acctManager.containsAcct(winner.FROM)) {
          Account accountFrom = this.acctManager.getAccount(winner.FROM);
          synchronized (accountFrom.ID) {                 
            accountFrom.remove(-1 * winner.PRICE, winner.GOOD);
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
	  Market market = this.manager.getMarket(marketID); 
	  while (!market.isInnerOver()) { 
	    this.innerCycle(marketID, agentID);
	  }
	  this.innerCycle(marketID, agentID);
	}
	
	public synchronized void completeAuction(Integer marketID) throws InterruptedException { 
	  //run every outer cycle of the auction until it is terminated per the outer termination condition.
	  Market market = this.manager.getMarket(marketID); 
	 while(!market.isOverOuter()) {
	   while(!market.isInnerOver()) {
       Thread.sleep(1000);
	     this.updateAllAuctions(true);
	     Thread.sleep(1000);
	   }
	   this.updateAllAuctions(true);
     Thread.sleep(1000);
	 }
   this.manager.close(this, marketID);
	}

	/*
	 * Sends a MarketUpdate about this specific market to all agents
	 * 
	 * @param Security : the market to update on
	 */
	public void sendMarketUpdate(ITwoSidedAuctionChannel market) {
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
			throw new NullPointerException("ERROR: Null registration ID");
		}
		this.theServer.sendToTCP(connection.getID(), new AckMessage(registration, false));
		Collection<Integer> allIds = connections.values();
		Integer theID = registration.getID();
		if (allIds.contains(theID)) {
		  Logging.log("AbsServer-defaultRegistration: attempting to register an ID that already exists.");
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
			Account newAccount = new Account(theID);
			newAccount.add(initialMonies);
			for (ITradeable t : this.initialGoods)
			newAccount.add(0.0, t);
			this.acctManager.setAccount(theID, newAccount);
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
